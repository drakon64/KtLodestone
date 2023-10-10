package cloud.drakon.ktlodestone.character

import cloud.drakon.ktlodestone.iconlayers.IconLayers

/** A [Character]'s [FreeCompany] or [PvpTeam]. */
data class Guild(val name: String, val id: String, val iconLayers: IconLayers)

/** A [Character]'s Free Company. */
typealias FreeCompany = Guild

/** A [Character]'s PvP Team. */
typealias PvpTeam = Guild
