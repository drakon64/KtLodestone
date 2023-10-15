package cloud.drakon.ktlodestone.cwls.search

import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.Region

/** A search result from *The Lodestone*'s `/crossworld_linkshell` endpoint. */
data class CrossWorldLinkshellSearchResult(
    /** The Cross-world Linkshell's name. */
    val name: String,
    /** The Cross-world Linkshell's ID. */
    val id: String,
    /** The Cross-world Linkshell's data center. */
    val dataCenter: DataCenter,
    /** The Cross-world Linkshell's region. */
    val region: Region,
    /** The Cross-world Linkshell's active members. */
    val activeMembers: Int,
)
