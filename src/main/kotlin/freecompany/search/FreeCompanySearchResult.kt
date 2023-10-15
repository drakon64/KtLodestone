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
    /** The Free Company's crest. */
    val crest: IconLayers,
    /** The world that the Free Company is on. */
    val world: World,
    /** The data center that the Free Company is on. */
    val dataCenter: DataCenter,
    /** The Free Company's active members. */
    val activeMembers: Int,
    /** The status of the Free Company's estate. */
    val estateBuilt: Housing,
    /** When the Free Company was formed. */
    val formed: Long,
    /** When the Free Company is active. */
    val active: Active?,
    /** Whether the Free Company is recruiting. */
    val recruitment: Recruitment,
    /** The Free Company's focus. */
    val focus: List<Focus>?,
    /** Roles that the Free Company is seeking. */
    val seeking: List<Seeking>?,
)
