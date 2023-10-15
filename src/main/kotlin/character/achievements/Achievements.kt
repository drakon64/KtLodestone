package cloud.drakon.ktlodestone.character.achievements

/** Achievements unlocked by a character. */
data class Achievements(
    val achievements: List<Achievement>,
    val points: Short,
    val total: Short,
)
