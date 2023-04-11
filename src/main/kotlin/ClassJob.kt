package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.ProfileClassJob
import cloud.drakon.ktlodestone.profile.classjob.Bozja
import cloud.drakon.ktlodestone.profile.classjob.ClassJobLevel
import cloud.drakon.ktlodestone.profile.classjob.Eureka
import cloud.drakon.ktlodestone.profile.classjob.Experience
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object ClassJob {
    suspend fun getClassJob(id: Int) = coroutineScope {
        val request =
            ktorClient.get("https://eu.finalfantasyxiv.com/lodestone/character/${id}/class_job/")
        val character = when (request.status.value) {
            200 -> Jsoup.parse(request.body() as String)
            404 -> throw CharacterNotFoundException("Thrown when a character could not be found on The Lodestone.")
            else -> throw LodestoneException("Thrown when The Lodestone returns an unknown error.")
        }

        val bozja = async { getResistanceRank(character) }
        val eureka = async { getElementalLevel(character) }
        val paladin = async { getProfileClassJob(character, "Paladin") }
        val warrior = async { getProfileClassJob(character, "Warrior") }
        val darkKnight = async { getProfileClassJob(character, "Dark Knight") }
        val gunbreaker = async { getProfileClassJob(character, "Gunbreaker") }

        val whiteMage = async { getProfileClassJob(character, "White Mage") }
        val scholar = async { getProfileClassJob(character, "Scholar") }
        val astrologian = async { getProfileClassJob(character, "Astrologian") }
        val sage = async { getProfileClassJob(character, "Sage") }

        val monk = async { getProfileClassJob(character, "Monk") }
        val dragoon = async { getProfileClassJob(character, "Dragoon") }
        val ninja = async { getProfileClassJob(character, "Ninja") }
        val samurai = async { getProfileClassJob(character, "Samurai") }
        val reaper = async { getProfileClassJob(character, "Reaper") }

        val bard = async { getProfileClassJob(character, "Bard") }
        val machinist = async { getProfileClassJob(character, "Machinist") }
        val dancer = async { getProfileClassJob(character, "Dancer") }

        val blackMage = async { getProfileClassJob(character, "Black Mage") }
        val summoner = async { getProfileClassJob(character, "Summoner") }
        val redMage = async { getProfileClassJob(character, "Red Mage") }
        val blueMage = async { getProfileClassJob(character, "Blue Mage") }

        return@coroutineScope ProfileClassJob(
            bozja.await(),
            eureka.await(),
            paladin.await(),
            warrior.await(),
            darkKnight.await(),
            gunbreaker.await(),
            whiteMage.await(),
            scholar.await(),
            astrologian.await(),
            sage.await(),
            monk.await(),
            dragoon.await(),
            ninja.await(),
            samurai.await(),
            reaper.await(),
            bard.await(),
            machinist.await(),
            dancer.await(),
            blackMage.await(),
            summoner.await(),
            redMage.await(),
            blueMage.await(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
        )
    }

    private val currentBozjaExperienceRegex = """\+d""".toRegex()
    private val bozjaExperienceToNextLevelRegex =
        """(?<=Mettle to Next Rank: )\d+""".toRegex()

    private suspend fun getResistanceRank(character: Document) = coroutineScope {
        val resistanceRank = async {
            character.select("div.character__job__list:nth-child(7) > div.character__job__level")
                .first()
                ?.text()
                ?.toByteOrNull()
        }

        if (resistanceRank.await() != null) {
            val experience = async {
                character.select("div.character__job__list:nth-child(7) > div.character__job__exp")
                    .text()
            }

            if (experience.await() == "Current Mettle: -- / Mettle to Next Rank: --") {
                return@coroutineScope Bozja(
                    level = resistanceRank.await() !!, mettle = null
                )
            } else {
                val currentExperience = async {
                    currentBozjaExperienceRegex.find(experience.await()) !!.value.toInt()
                }
                val experienceToNextLevel = async {
                    bozjaExperienceToNextLevelRegex.find(experience.await()) !!.value.toInt()
                }

                return@coroutineScope Bozja(
                    level = resistanceRank.await() !!.toByte(), mettle = Experience(
                        current = currentExperience.await(),
                        next = experienceToNextLevel.await()
                    )
                )
            }
        } else {
            return@coroutineScope null
        }
    }

    private val currentExperienceRegex = """^[^ /]*""".toRegex()
    private val experienceToNextLevelRegex = """(?<=/ ).*""".toRegex()

    private suspend fun getElementalLevel(character: Document) = coroutineScope {
        val elementalLevel = async {
            character.select("#character > div.character__content.selected > div:nth-child(9) > div.character__job__level")
                .first()
                ?.text()
                ?.toByteOrNull()
        }

        if (elementalLevel.await() != null) {
            val experience = async {
                character.select("#character > div.character__content.selected > div:nth-child(9) > div.character__job__exp")
                    .text()
            }

            if (experience.await() == "-- / --") {
                return@coroutineScope Eureka(
                    level = elementalLevel.await() !!, experience = null
                )
            } else {
                val currentExperience = async {
                    currentExperienceRegex.find(experience.await()) !!.value.replace(
                        ",", ""
                    ).toInt()
                }
                val experienceToNextLevel = async {
                    experienceToNextLevelRegex.find(experience.await()) !!.value.replace(
                        ",", ""
                    ).toInt()
                }

                return@coroutineScope Eureka(
                    level = elementalLevel.await() !!.toByte(), experience = Experience(
                        current = currentExperience.await(),
                        next = experienceToNextLevel.await()
                    )
                )
            }
        } else {
            return@coroutineScope null
        }
    }

    private suspend fun getProfileClassJob(character: Document, classJob: String) =
        coroutineScope {
            class ClassJobSelector(
                val name: String,
                val level: String,
                val experience: String,
            )

            val classJobSelectors = mapOf(
                "Paladin" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(1) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(1) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(1) > div.character__job__exp"
                ), "Warrior" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(1) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(1) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(1) > div.character__job__exp"
                ), "Dark Knight" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(3) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(3) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(3) > div.character__job__exp"
                ), "Gunbreaker" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(4) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(4) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(1) > ul > li:nth-child(4) > div.character__job__exp"
                ),

                "White Mage" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(1) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(1) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(1) > div.character__job__exp"
                ), "Scholar" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(2) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(2) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(2) > div.character__job__exp"
                ), "Astrologian" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(3) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(3) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(4) > div.character__job__exp"
                ), "Sage" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(4) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(4) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(4) > div.character__job__exp"
                ),

                "Monk" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(1) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(1) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(1) > div.character__job__exp"
                ), "Dragoon" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(2) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(2) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(2) > div.character__job__exp"
                ), "Ninja" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(3) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(3) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(3) > div.character__job__exp"
                ), "Samurai" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(4) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(4) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(4) > div.character__job__exp"
                ), "Reaper" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(5) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(5) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(1) > ul > li:nth-child(5) > div.character__job__exp"
                ),

                "Bard" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div.character__job__exp"
                ), "Machinist" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div.character__job__exp"
                ), "Dancer" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div.character__job__exp"
                ),

                "Black Mage" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(1) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(1) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(1) > div.character__job__exp"
                ), "Summoner" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(2) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(2) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(2) > div.character__job__exp"
                ), "Red Mage" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(3) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(3) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(3) > div.character__job__exp"
                ), "Blue Mage" to ClassJobSelector(
                    name = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(4) > div.character__job__name.js__tooltip",
                    level = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(4) > div.character__job__level",
                    experience = "#character > div.character__content.selected > div:nth-child(3) > div:nth-child(2) > ul:nth-child(4) > li:nth-child(4) > div.character__job__exp"
                )
            )

            val selectors = classJobSelectors.getValue(classJob)

            val name = async {
                character.select(selectors.name).first() !!.text()
            }
            val level = async {
                character.select(selectors.level).first() !!.text()
            }
            val experience = async {
                character.select(selectors.experience).first() !!.text()
            }

            if (level.await() == "-" && experience.await() == "-- / --") {
                return@coroutineScope null
            } else if (experience.await() == "-- / --") {
                return@coroutineScope ClassJobLevel(
                    name = name.await(),
                    level = level.await().toByte(),
                    experience = null
                )
            } else {
                val currentExperience = async {
                    currentExperienceRegex.find(experience.await()) !!.value.replace(
                        ",", ""
                    ).toInt()
                }
                val experienceToNextLevel = async {
                    experienceToNextLevelRegex.find(experience.await()) !!.value.replace(
                        ",", ""
                    ).toInt()
                }

                return@coroutineScope ClassJobLevel(
                    name = name.await(),
                    level = level.await().toByte(),
                    experience = Experience(
                        current = currentExperience.await(),
                        next = experienceToNextLevel.await()
                    )
                )
            }
        }
}
