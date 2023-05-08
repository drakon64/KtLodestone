package cloud.drakon.ktlodestone.profile

import cloud.drakon.ktlodestone.KtLodestone
import cloud.drakon.ktlodestone.profile.achievements.Achievement
import cloud.drakon.ktlodestone.profile.achievements.ProfileAchievements
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

                achievements.add(
                    Achievement(
                        name.await(), id.await(), date.await()
                    )
                )
            }

        return@coroutineScope achievements
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
