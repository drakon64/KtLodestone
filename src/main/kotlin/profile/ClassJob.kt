package cloud.drakon.ktlodestone.profile

import cloud.drakon.ktlodestone.KtLodestone
import cloud.drakon.ktlodestone.profile.classjob.ClassJobLevel
import cloud.drakon.ktlodestone.profile.classjob.ClassJobName
import cloud.drakon.ktlodestone.profile.classjob.Experience
import cloud.drakon.ktlodestone.profile.classjob.ProfileClassJob
import cloud.drakon.ktlodestone.profile.classjob.UniqueDutyLevel
import cloud.drakon.ktlodestone.profile.classjob.UniqueDutyName
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.nodes.Document

internal object ClassJob {
    private val lodestoneCssSelectors = Json.parseToJsonElement(
        this::class.java.classLoader.getResource("lodestone-css-selectors/profile/classjob.json") !!
            .readText()
    )

    private const val noExperience = "-- / --"

    suspend fun getClassJob(id: Int) = coroutineScope {
        val character = KtLodestone.getLodestoneProfile(id, "class_job")

        val uniqueDutyLevels = async { getUniqueDutyLevels(character) }
        val classJobLevels = async { getClassJobLevels(character) }

        ProfileClassJob(
            classJobLevels.await(), uniqueDutyLevels.await()
        )
    }

    private val currentBozjaExperienceRegex = """\+d""".toRegex()
    private val bozjaExperienceToNextLevelRegex =
        """(?<=Mettle to Next Rank: )\d+""".toRegex()
    private val currentExperienceRegex = """^[^ /]*""".toRegex()
    private val experienceToNextLevelRegex = """(?<=/ ).*""".toRegex()

    private suspend fun getUniqueDutyLevels(character: Document) = coroutineScope {
        val uniqueDuties = mutableMapOf<UniqueDutyName, UniqueDutyLevel?>()

        UniqueDutyName.values().forEach {
            uniqueDuties[it] = getUniqueDutyLevel(character, it)
        }

        return@coroutineScope uniqueDuties.toMap()
    }

    private suspend fun getUniqueDutyLevel(character: Document, duty: UniqueDutyName) =
        coroutineScope {
            val uniqueDutyLevel = async { getLevel(character, duty.name) }

            if (uniqueDutyLevel.await() != null) {
                val experience = async { getExperience(character, duty.name) }

                if (duty == UniqueDutyName.BOZJA) {
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

    private suspend fun getClassJobLevels(character: Document) = coroutineScope {
        val classesJobs = mutableMapOf<ClassJobName, ClassJobLevel?>()

        ClassJobName.values().forEach {
            classesJobs[it] = getClassJobLevel(character, it)
        }

        return@coroutineScope classesJobs.toMap()
    }

    private suspend fun getClassJobLevel(character: Document, classJob: ClassJobName) =
        coroutineScope {
            val name = async {
                getUnlockState(character, classJob.name)
            }
            val level = async {
                getLevel(character, classJob.name)
            }
            val experience = async {
                getExperience(character, classJob.name)
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
