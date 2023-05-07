package cloud.drakon.ktlodestone.profile.character

/**
 * The active class/job of a character.
 * @property name The name of the currently active class/job for a character.
 * @property level The level of the currently active class/job for a character.
 */
data class ActiveClassJob(val name: String, val level: Byte)
