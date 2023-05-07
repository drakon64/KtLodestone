package cloud.drakon.ktlodestone.profile.minions

/**
 * The minions that a character has acquired.
 * @property minions A [Map] of minion names to a [Minion] class.
 * @property total The amount of minions that a character has acquired.
 */
data class ProfileMinions(val minions: Map<String, Minion>, val total: Short)
