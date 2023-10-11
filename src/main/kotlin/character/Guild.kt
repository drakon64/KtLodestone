package cloud.drakon.ktlodestone.character

import cloud.drakon.ktlodestone.iconlayers.IconLayers
import cloud.drakon.ktlodestone.searchLodestoneCharacter

/** A character's Free Company or  PvP Team. */
data class Guild(
    /** The name of the Free Company or PvP Team. */
    val name: String,
    /** The ID of the Free Company or PvP Team. */
    val id: String,
    /** The layers that make up the icon of the Free Company or PvP Team. Always `null` when returned by [searchLodestoneCharacter]. */
    val iconLayers: IconLayers?,
)

/** A character's Free Company. */
typealias FreeCompany = Guild

/** A character's PvP Team. */
typealias PvpTeam = Guild
