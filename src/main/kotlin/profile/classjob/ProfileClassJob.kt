package cloud.drakon.ktlodestone.profile.classjob

/**
 * The level(s) that a character has in their unlocked classes/jobs and unique duties.
 * @property classJob A [Map] of [ClassJobName] to [ClassJobLevel].
 * @property uniqueDuty A [Map] of [UniqueDutyName] to [UniqueDutyLevel].
 */
data class ProfileClassJob(
    val classJob: Map<ClassJobName, ClassJobLevel?>,
    val uniqueDuty: Map<UniqueDutyName, UniqueDutyLevel?>,
)
