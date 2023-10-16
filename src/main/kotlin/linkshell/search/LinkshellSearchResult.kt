package cloud.drakon.ktlodestone.linkshell.search

import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.Region
import cloud.drakon.ktlodestone.world.World

/** A search result from The Lodestone's `/linkshell` or `/crossworld_linkshell` endpoint. */
data class LinkshellSearchResult(
    /** The Linkshell's name. */
    val name: String,
    /** The Linkshell's ID. */
    val id: String,
    /** The Linkshell's world. Always `null` if [crossWorld] is `true`, otherwise never `null`. */
    val world: World?,
    /** The Linkshell's data center. */
    val dataCenter: DataCenter,
    /** The Linkshell's region. */
    val region: Region,
    /** The Linkshell's active members. */
    val activeMembers: Int,
    /** If this is a Cross-world Linkshell or not. */
    val crossWorld: Boolean,
)
