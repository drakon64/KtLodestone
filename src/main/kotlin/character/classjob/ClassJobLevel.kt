package cloud.drakon.ktlodestone.character.classjob

/** A characters level and experience with a class/job. */
data class ClassJobLevel(
    /** A characters level with a class/job. */
    val level: Byte,
    /** A characters experience with a class/job. */
    val experience: Experience?,
)
