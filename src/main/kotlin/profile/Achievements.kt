package cloud.drakon.ktlodestone.profile

import cloud.drakon.ktlodestone.exception.PagesLessThanOneException
import cloud.drakon.ktlodestone.profile.achievements.Achievement
import cloud.drakon.ktlodestone.profile.achievements.ProfileAchievements
import kotlin.collections.set
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.nodes.Document

internal object Achievements {
    private val lodestoneCssSelectors = Json.parseToJsonElement(
        this::class.java.classLoader.getResource("lodestone-css-selectors/profile/achievements.json") !!
            .readText()
    )

    suspend fun getAchievements(id: Int, pages: Byte?) = coroutineScope {
        if (pages != null && pages < 1) {
            throw PagesLessThanOneException("`pages` must be at least 1.")
        }

        val character = Profile.getLodestoneProfile(id, "achievement")

        val achievements = async { getProfileAchievements(character, id, pages) }

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

    private suspend fun getProfileAchievements(
        character: Document,
        id: Int,
        pages: Byte?,
    ) = coroutineScope {
        val achievements = mutableMapOf<Short, Achievement>()

        getPaginatedAchievements(
            "https://eu.finalfantasyxiv.com/lodestone/character/${id}/achievement",
            achievements
        )

        var next =
            character.select(lodestoneCssSelectors.jsonObject["LIST_NEXT_BUTTON"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .attr("href")

        if (pages != null) {
            var page: Byte = 1

            while (next != "javascript:void(0);" && page != pages) {
                next = getPaginatedAchievements(next, achievements)
                page = (page + 1).toByte()
            }
        } else {
            while (next != "javascript:void(0);") {
                next = getPaginatedAchievements(next, achievements)
            }
        }

        return@coroutineScope achievements.toMap()
    }

    private val achievementNameRegex = """"([^"]*)"""".toRegex()
    private val achievementIdRegex = """\d+(?=\/$)""".toRegex()
    private val achievementDateRegex = """(?<=ldst_strftime\()\d+""".toRegex()

    private suspend fun getPaginatedAchievements(
        page: String,
        achievementsList: MutableMap<Short, Achievement>,
    ) = coroutineScope {
        val character = Profile.getLodestoneProfilePaginated(page)

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

                achievementsList[id.await()] =
                    Achievement(name.await(), id.await(), date.await())
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
