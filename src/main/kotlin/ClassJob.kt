package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.ClassesJobs
import cloud.drakon.ktlodestone.profile.ProfileClassJob
import cloud.drakon.ktlodestone.profile.classjob.Bozja
import cloud.drakon.ktlodestone.profile.classjob.ClassJobLevel
import cloud.drakon.ktlodestone.profile.classjob.Eureka
import cloud.drakon.ktlodestone.profile.classjob.Experience
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.Jsoup
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
        val request =
            ktorClient.get("https://eu.finalfantasyxiv.com/lodestone/character/${id}/class_job/")
        val character = when (request.status.value) {
            200 -> Jsoup.parse(request.body() as String)
            404 -> throw CharacterNotFoundException("Thrown when a character could not be found on The Lodestone.")
            else -> throw LodestoneException("Thrown when The Lodestone returns an unknown error.")
        }

        val bozja = async { getResistanceRank(character) }
        val eureka = async { getElementalLevel(character) }
        val paladin = async { getClassJobLevel(character, ClassesJobs.PALADIN) }
        val warrior = async { getClassJobLevel(character, ClassesJobs.WARRIOR) }
        val darkKnight = async { getClassJobLevel(character, ClassesJobs.DARKKNIGHT) }
        val gunbreaker = async { getClassJobLevel(character, ClassesJobs.GUNBREAKER) }

        val whiteMage = async { getClassJobLevel(character, ClassesJobs.WHITEMAGE) }
        val scholar = async { getClassJobLevel(character, ClassesJobs.SCHOLAR) }
        val astrologian = async { getClassJobLevel(character, ClassesJobs.ASTROLOGIAN) }
        val sage = async { getClassJobLevel(character, ClassesJobs.SAGE) }

        val monk = async { getClassJobLevel(character, ClassesJobs.MONK) }
        val dragoon = async { getClassJobLevel(character, ClassesJobs.DRAGOON) }
        val ninja = async { getClassJobLevel(character, ClassesJobs.NINJA) }
        val samurai = async { getClassJobLevel(character, ClassesJobs.SAMURAI) }
        val reaper = async { getClassJobLevel(character, ClassesJobs.REAPER) }

        val bard = async { getClassJobLevel(character, ClassesJobs.BARD) }
        val machinist = async { getClassJobLevel(character, ClassesJobs.MACHINIST) }
        val dancer = async { getClassJobLevel(character, ClassesJobs.DANCER) }

        val blackMage = async { getClassJobLevel(character, ClassesJobs.BLACKMAGE) }
        val summoner = async { getClassJobLevel(character, ClassesJobs.SUMMONER) }
        val redMage = async { getClassJobLevel(character, ClassesJobs.REDMAGE) }
        val blueMage = async { getClassJobLevel(character, ClassesJobs.BLUEMAGE) }

        val carpenter = async { getClassJobLevel(character, ClassesJobs.CARPENTER) }
        val blacksmith = async { getClassJobLevel(character, ClassesJobs.BLACKSMITH) }
        val armorer = async { getClassJobLevel(character, ClassesJobs.ARMORER) }
        val goldsmith = async { getClassJobLevel(character, ClassesJobs.GOLDSMITH) }
        val leatherworker = async {
            getClassJobLevel(character, ClassesJobs.LEATHERWORKER)
        }
        val weaver = async { getClassJobLevel(character, ClassesJobs.WEAVER) }
        val alchemist = async { getClassJobLevel(character, ClassesJobs.ALCHEMIST) }
        val culinarian = async { getClassJobLevel(character, ClassesJobs.CULINARIAN) }

        val miner = async { getClassJobLevel(character, ClassesJobs.MINER) }
        val botanist = async { getClassJobLevel(character, ClassesJobs.BOTANIST) }
        val fisher = async { getClassJobLevel(character, ClassesJobs.FISHER) }

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

    private suspend fun getResistanceRank(character: Document) = coroutineScope {
        val classJob = ClassesJobs.BOZJA

        val resistanceRank = async { getLevel(character, classJob) }

        if (resistanceRank.await() != null) {
            val experience = async { getExperience(character, classJob) }

            if (experience.await() == "Current Mettle: -- / Mettle to Next Rank: --") {
                Bozja(level = resistanceRank.await() !!.toByte(), mettle = null)
            } else {
                val currentExperience = async {
                    currentBozjaExperienceRegex.find(experience.await()) !!.value.toInt()
                }
                val experienceToNextLevel = async {
                    bozjaExperienceToNextLevelRegex.find(experience.await()) !!.value.toInt()
                }

                Bozja(
                    level = resistanceRank.await() !!.toByte(), mettle = Experience(
                        current = currentExperience.await(),
                        next = experienceToNextLevel.await()
                    )
                )
            }
        } else {
            null
        }
    }

    private val currentExperienceRegex = """^[^ /]*""".toRegex()
    private val experienceToNextLevelRegex = """(?<=/ ).*""".toRegex()

    private suspend fun getElementalLevel(character: Document) = coroutineScope {
        val classJob = ClassesJobs.EUREKA

        val elementalLevel = async { getLevel(character, classJob) }

        if (elementalLevel.await() != null) {
            val experience = async { getExperience(character, classJob) }

            if (experience.await() == noExperience) {
                Eureka(
                    level = elementalLevel.await() !!.toByte(), experience = null
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

                Eureka(
                    level = elementalLevel.await() !!.toByte(), experience = Experience(
                        current = currentExperience.await(),
                        next = experienceToNextLevel.await()
                    )
                )
            }
        } else {
            null
        }
    }

    private suspend fun getClassJobLevel(character: Document, classJob: ClassesJobs) =
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

    private suspend fun getLevel(character: Document, classJob: ClassesJobs) =
        coroutineScope {
            val selectorJson =
                lodestoneCssSelectors.jsonObject[classJob.name] !!.jsonObject["LEVEL"] !!.jsonObject["selector"] !!.jsonPrimitive.content

            character.select(selectorJson).first()?.text()
        }

    private suspend fun getExperience(character: Document, classJob: ClassesJobs) =
        coroutineScope {
            val experience = if (classJob != ClassesJobs.BOZJA) {
                "EXP"
            } else {
                "METTLE"
            }

            val selectorJson =
                lodestoneCssSelectors.jsonObject[classJob.name] !!.jsonObject[experience] !!.jsonObject["selector"] !!.jsonPrimitive.content

            character.select(selectorJson).first() !!.text()
        }

    private suspend fun getUnlockState(character: Document, classJob: ClassesJobs) =
        coroutineScope {
            character.select(lodestoneCssSelectors.jsonObject[classJob.name] !!.jsonObject["UNLOCKSTATE"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .text()
        }
}
