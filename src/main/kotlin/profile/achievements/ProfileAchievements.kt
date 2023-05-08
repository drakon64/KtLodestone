package cloud.drakon.ktlodestone.profile.achievements

/**
 * The achievements that a character has acquired.
 * @property achievements A [Map] of achievement ID's to [Achievement].
 * @property total The amount of achievements that a character has acquired.
 * @property points The amount of achievement points that a character has acquired.
 */
data class ProfileAchievements(
    val achievements: Map<Short, Achievement>,
    val total: Short,
    val points: Short,
)
