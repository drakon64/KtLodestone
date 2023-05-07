package cloud.drakon.ktlodestone.profile.classjob

/**
 * The experience that a character has.
 * @property current The experience that the character currently has.
 * @property next The experience required for the character to reach the next level.
 */
data class Experience(val current: Int, val next: Int)
