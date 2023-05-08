package cloud.drakon.ktlodestone.profile.achievements

/**
 * An achievement that a character has acquired.
 * @property name The name of the achievement.
 * @property id The ID of the achievement.
 * @property date The date that the achievement was acquired in Unix time.
 */
data class Achievement(val name: String, val id: Short, val date: Long)
