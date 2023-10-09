package cloud.drakon.ktlodestone.character

import cloud.drakon.ktlodestone.IconLayers

/** A [Character]'s [FreeCompany] or [PvpTeam]. */
data class Guild(val name: String, val id: Int, val iconLayers: IconLayers)

/** A [Character]'s Free Company. */
typealias FreeCompany = Guild

/** A [Character]'s PvP Team. */
typealias PvpTeam = Guild
