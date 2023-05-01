package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.classjob.ClassJobLevel
import cloud.drakon.ktlodestone.profile.classjob.Experience
import cloud.drakon.ktlodestone.profile.classjob.ProfileClassJob
import cloud.drakon.ktlodestone.profile.classjob.UniqueDutyLevel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.nodes.Document

object ClassJob {
    private val lodestoneCssSelectors = Json.parseToJsonElement(
        this::class.java.classLoader.getResource("lodestone-css-selectors/profile/classjob.json") !!
            .readText()
    )

    private const val noExperience = "-- / --"

    /**
     * Gets a characters class/job stats from The Lodestone
     * @param id The Lodestone character ID
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    suspend fun getClassJob(id: Int) = coroutineScope {
        val character = getLodestoneProfile(id)

        val bozja = async { getUniqueDutyLevel(character, "BOZJA") }
        val eureka = async { getUniqueDutyLevel(character, "EUREKA") }
        val paladin = async { getClassJobLevel(character, "PALADIN") }
        val warrior = async { getClassJobLevel(character, "WARRIOR") }
        val darkKnight = async { getClassJobLevel(character, "DARKKNIGHT") }
        val gunbreaker = async { getClassJobLevel(character, "GUNBREAKER") }

        val whiteMage = async { getClassJobLevel(character, "WHITEMAGE") }
        val scholar = async { getClassJobLevel(character, "SCHOLAR") }
        val astrologian = async { getClassJobLevel(character, "ASTROLOGIAN") }
        val sage = async { getClassJobLevel(character, "SAGE") }

        val monk = async { getClassJobLevel(character, "MONK") }
        val dragoon = async { getClassJobLevel(character, "DRAGOON") }
        val ninja = async { getClassJobLevel(character, "NINJA") }
        val samurai = async { getClassJobLevel(character, "SAMURAI") }
        val reaper = async { getClassJobLevel(character, "REAPER") }

        val bard = async { getClassJobLevel(character, "BARD") }
        val machinist = async { getClassJobLevel(character, "MACHINIST") }
        val dancer = async { getClassJobLevel(character, "DANCER") }

        val blackMage = async { getClassJobLevel(character, "BLACKMAGE") }
        val summoner = async { getClassJobLevel(character, "SUMMONER") }
        val redMage = async { getClassJobLevel(character, "REDMAGE") }
        val blueMage = async { getClassJobLevel(character, "BLUEMAGE") }

        val carpenter = async { getClassJobLevel(character, "CARPENTER") }
        val blacksmith = async { getClassJobLevel(character, "BLACKSMITH") }
        val armorer = async { getClassJobLevel(character, "ARMORER") }
        val goldsmith = async { getClassJobLevel(character, "GOLDSMITH") }
        val leatherworker = async { getClassJobLevel(character, "LEATHERWORKER") }
        val weaver = async { getClassJobLevel(character, "WEAVER") }
        val alchemist = async { getClassJobLevel(character, "ALCHEMIST") }
        val culinarian = async { getClassJobLevel(character, "CULINARIAN") }

        val miner = async { getClassJobLevel(character, "MINER") }
        val botanist = async { getClassJobLevel(character, "BOTANIST") }
        val fisher = async { getClassJobLevel(character, "FISHER") }

        ProfileClassJob(
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
            carpenter.await(),
            blacksmith.await(),
            armorer.await(),
            goldsmith.await(),
            leatherworker.await(),
            weaver.await(),
            alchemist.await(),
            culinarian.await(),
            miner.await(),
            botanist.await(),
            fisher.await(),
        )
    }

    private val currentBozjaExperienceRegex = """\+d""".toRegex()
    private val bozjaExperienceToNextLevelRegex =
        """(?<=Mettle to Next Rank: )\d+""".toRegex()
    private val currentExperienceRegex = """^[^ /]*""".toRegex()
    private val experienceToNextLevelRegex = """(?<=/ ).*""".toRegex()

    private suspend fun getUniqueDutyLevel(character: Document, duty: String) =
        coroutineScope {
            val uniqueDutyLevel = async { getLevel(character, duty) }

            if (uniqueDutyLevel.await() != null) {
                val experience = async { getExperience(character, duty) }

                if (duty == "BOZJA") {
                    if (experience.await() == "Current Mettle: -- / Mettle to Next Rank: --") {
                        UniqueDutyLevel(
                            level = uniqueDutyLevel.await() !!.toByte(),
                            experience = null
                        )
                    } else {
                        val currentExperience = async {
                            currentBozjaExperienceRegex.find(experience.await()) !!.value.toInt()
                        }
                        val experienceToNextLevel = async {
                            bozjaExperienceToNextLevelRegex.find(experience.await()) !!.value.toInt()
                        }

                        UniqueDutyLevel(
                            level = uniqueDutyLevel.await() !!.toByte(),
                            experience = Experience(
                                current = currentExperience.await(),
                                next = experienceToNextLevel.await()
                            )
                        )
                    }
                } else if (experience.await() == noExperience) {
                    UniqueDutyLevel(
                        level = uniqueDutyLevel.await() !!.toByte(), experience = null
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

                    UniqueDutyLevel(
                        level = uniqueDutyLevel.await() !!.toByte(),
                        experience = Experience(
                            current = currentExperience.await(),
                            next = experienceToNextLevel.await()
                        )
                    )
                }
            } else {
                null
            }
        }

    private suspend fun getClassJobLevel(character: Document, classJob: String) =
        coroutineScope {
            val name = async {
                getUnlockState(character, classJob)
            }
            val level = async {
                getLevel(character, classJob)
            }
            val experience = async {
                getExperience(character, classJob)
            }

            if (level.await() == "-") {
                null
            } else if (experience.await() == noExperience) {
                ClassJobLevel(
                    name = name.await(),
                    level = level.await() !!.toByte(),
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

                ClassJobLevel(
                    name = name.await(),
                    level = level.await() !!.toByte(),
                    experience = Experience(
                        current = currentExperience.await(),
                        next = experienceToNextLevel.await()
                    )
                )
            }
        }

    private suspend fun getLevel(character: Document, classJob: String) =
        coroutineScope {
            val selectorJson =
                lodestoneCssSelectors.jsonObject[classJob] !!.jsonObject["LEVEL"] !!.jsonObject["selector"] !!.jsonPrimitive.content

            character.select(selectorJson).first()?.text()
        }

    private suspend fun getExperience(character: Document, classJob: String) =
        coroutineScope {
            val experience = if (classJob != "BOZJA") {
                "EXP"
            } else {
                "METTLE"
            }

            val selectorJson =
                lodestoneCssSelectors.jsonObject[classJob] !!.jsonObject[experience] !!.jsonObject["selector"] !!.jsonPrimitive.content

            character.select(selectorJson).first() !!.text()
        }

    private suspend fun getUnlockState(character: Document, classJob: String) =
        coroutineScope {
            character.select(lodestoneCssSelectors.jsonObject[classJob] !!.jsonObject["UNLOCKSTATE"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .text()
        }
}
