package cloud.drakon.ktlodestone.profile.guild

/**
 * The Free Company/PvP Team that a character is a member of.
 * @property name The name of the Free Company/PvP Team.
 * @property id The ID of the Free Company/PvP Team.
 * @property iconLayers The icon layers of the Free Company/PvP Team.
 */
data class Guild(val name: String, val id: String, val iconLayers: IconLayers)

typealias FreeCompany = Guild
typealias PvpTeam = Guild
