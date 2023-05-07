package cloud.drakon.ktlodestone.profile.classjob

/**
 * The Elemental Level/Resistance Rank of a character.
 * @property level The level of a character in the unique duty.
 * @property experience The experience that a character has in the unique duty.
 */
data class UniqueDutyLevel(val level: Byte, val experience: Experience?)
