package cloud.drakon.ktlodestone.character.achievements

import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.ktorClient
import cloud.drakon.ktlodestone.selectors.character.achievements.AchievementsSelectors
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import org.jsoup.select.Elements

// To ensure that the variables aren't accessed statically, these functions get their own class
internal class AchievementsSearch {
    private companion object {
        // This function can be accessed statically
        private suspend fun scrapeAchievements(elements: Elements) = coroutineScope {
            buildList {
                elements.select(AchievementsSelectors.ENTRIES_ROOT).forEach {
                    val name = async {
                        it.select(AchievementsSelectors.ENTRY_NAME).text().split('"')[1]
                    }

                    val id = async {
                        it.select(AchievementsSelectors.ENTRY_ID)
                            .attr(AchievementsSelectors.ENTRY_ID_ATTR)
                            .split('/')[6]
                            .toShort()
                    }

                    val date = async {
                        AchievementsSelectors.ENTRY_TIME_REGEX.find(
                            it.select(AchievementsSelectors.ENTRY_TIME).html()
                        )!!.value.toLong()
                    }

                    add(Achievement(name.await(), id.await(), date.await()))
                }
            }
        }
    }

    private var nextPage = true
    private var first = true
    private var achievementPoints: Short = 0
    private var totalAchievements: Short = 0

    internal suspend fun scrapeCharacterAchievements(
        id: Int,
        pages: Byte,
    ) = coroutineScope {
        var page: Byte = 1

        val achievements = async {
            buildList {
                while (page <= pages && nextPage) {
                    add(
                        scrapeAchievements(
                            getLodestoneCharacterAchievementsPaginated(id, page)
                        )
                    )

                    page++
                }
            }.flatten()
        }

        Achievements(achievements.await(), achievementPoints, totalAchievements)
    }

    private suspend fun getLodestoneCharacterAchievementsPaginated(
        id: Int,
        page: Byte,
    ) = ktorClient.get("character/$id/achievement/") {
        url {
            parameters.append("page", "$page")
        }
    }.let {
        when (it.status.value) {
            200 -> Jsoup.parse(it.body() as String).let {
                if (first) {
                    achievementPoints = it.select(AchievementsSelectors.ACHIEVEMENT_POINTS)
                        .text()
                        .toShort()
                    totalAchievements = AchievementsSelectors.TOTAL_ACHIEVEMENTS_REGEX.find(
                        it.select(AchievementsSelectors.TOTAL_ACHIEVEMENTS)
                            .text()
                    )!!.value.toShort()

                    first = false
                }

                if (it.select(AchievementsSelectors.LIST_NEXT_BUTTON)
                        .attr(AchievementsSelectors.LIST_NEXT_BUTTON_ATTR) == "javascript:void(0);"
                ) nextPage = false

                it.select(AchievementsSelectors.ROOT)
            }

            else -> throw LodestoneException()
        }
    }
}
