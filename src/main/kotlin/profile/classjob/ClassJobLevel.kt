package cloud.drakon.ktlodestone.profile.classjob

/**
 * The level of a character in a given class/job.
 * @property name The name of the class/job.
 * @property level The level of the class/job.
 * @property experience The experience that the character has in the class/job.
 */
data class ClassJobLevel(
    val name: String,
    val level: Byte,
    val experience: Experience?,
)
