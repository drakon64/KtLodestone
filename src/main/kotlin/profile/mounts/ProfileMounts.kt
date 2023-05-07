package cloud.drakon.ktlodestone.profile.mounts

/**
 * The mounts that a character has acquired.
 * @property mounts A [Map] of mount names to a [Mount] class.
 * @property total The amount of mounts that a character has acquired.
 */
data class ProfileMounts(val mounts: Map<String, Mount>, val total: Short)
