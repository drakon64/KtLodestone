package cloud.drakon.ktlodestone.profile

import cloud.drakon.ktlodestone.KtLodestone
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.achievements.Achievement
import cloud.drakon.ktlodestone.profile.achievements.ProfileAchievements
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

internal object Achievements {
    private val lodestoneCssSelectors = Json.parseToJsonElement(
        this::class.java.classLoader.getResource("lodestone-css-selectors/profile/achievements.json") !!
            .readText()
    )

    suspend fun getAchievements(id: Int) = coroutineScope {
        val character = KtLodestone.getLodestoneProfile(id, "achievement")

        val achievements = async { getProfileAchievements(character) }

        val totalAchievements = async {
            getAchievementCount(character, "TOTAL_ACHIEVEMENTS")
        }

        val achievementPoints = async {
            getAchievementCount(character, "ACHIEVEMENT_POINTS")
        }

        return@coroutineScope ProfileAchievements(
            achievements.await(), totalAchievements.await(), achievementPoints.await()
        )
    }

    private val achievementNameRegex = """"([^"]*)"""".toRegex()
    private val achievementIdRegex = """\d+(?=\/$)""".toRegex()
    private val achievementDateRegex = """(?<=ldst_strftime\()\d+""".toRegex()

    private suspend fun getProfileAchievements(character: Document) = coroutineScope {
        val root =
            character.select(lodestoneCssSelectors.jsonObject["ROOT"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!

        val achievements = mutableListOf<Achievement>()

        var next =
            character.select(lodestoneCssSelectors.jsonObject["LIST_NEXT_BUTTON"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .attr("href")

        while (next != "javascript:void(0);") {
            next = getPaginatedAchievements(next, achievements)
        }

        return@coroutineScope achievements
    }

    private suspend fun getPaginatedAchievements(
        page: String,
        achievementsList: MutableList<Achievement>,
    ) = coroutineScope {
        val request = KtLodestone.ktorClient.get(page) {
            header(
                HttpHeaders.UserAgent, KtLodestone.userAgentDesktop
            )
        }

        val character: Document

        when (request.status.value) {
            200 -> character = Jsoup.parse(request.body() as String)
            404 -> throw CharacterNotFoundException("")
            else -> throw LodestoneException("The Lodestone returned an unknown error.")
        }

        val root =
            character.select(lodestoneCssSelectors.jsonObject["ROOT"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!

        root.select(lodestoneCssSelectors.jsonObject["ENTRY"] !!.jsonObject["ROOT"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
            .forEach {
                val name = async {
                    achievementNameRegex.find(
                        it.select(getEntrySelector("NAME")).first() !!.text()
                    ) !!.value
                }

                val id = async {
                    achievementIdRegex.find(
                        it.select(getEntrySelector("ID")).first() !!.attr("href")
                    ) !!.value.toShort()
                }

                val date = async {
                    achievementDateRegex.find(
                        it.select(getEntrySelector("TIME")).first() !!.html()
                    ) !!.value.toLong()
                }

                achievementsList.add(
                    Achievement(
                        name.await(), id.await(), date.await()
                    )
                )
            }

        return@coroutineScope character.select(lodestoneCssSelectors.jsonObject["LIST_NEXT_BUTTON"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
            .first() !!
            .attr("href")
    }

    private suspend fun getEntrySelector(entry: String) = coroutineScope {
        return@coroutineScope lodestoneCssSelectors.jsonObject["ENTRY"] !!.jsonObject[entry] !!.jsonObject["selector"] !!.jsonPrimitive.content
    }

    private val achievementCountRegex = """\d+""".toRegex()

    private suspend fun getAchievementCount(character: Document, selector: String) =
        coroutineScope {
            return@coroutineScope achievementCountRegex.find(
                character.select(lodestoneCssSelectors.jsonObject[selector] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                    .first() !!
                    .text()
            ) !!.value.toShort()
        }
}
