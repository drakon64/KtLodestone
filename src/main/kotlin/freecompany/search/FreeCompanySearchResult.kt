package cloud.drakon.ktlodestone.freecompany.search

import cloud.drakon.ktlodestone.iconlayers.IconLayers
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World

/** A search result from *The Lodestone*'s `/freecompany` endpoint. */
data class FreeCompanySearchResult(
    /** The Free Company's name. */
    val name: String,
    /** The Free Company's ID. */
    val id: String,
    val crest: IconLayers,
    val world: World,
    val dataCenter: DataCenter,
    val activeMembers: Int,
    val estateBuilt: Housing,
    val formed: Long,
    val active: Active?,
    val recruitment: Recruitment,
)
