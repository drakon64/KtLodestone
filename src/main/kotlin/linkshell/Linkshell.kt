package cloud.drakon.ktlodestone.linkshell

import cloud.drakon.ktlodestone.character.search.CharacterSearchResult
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.Region

/** A Linkshell. */
data class Linkshell(
    /** The name of the Linkshell. */
    val name: String,
    /** When the Linkshell was formed. */
    val formed: Long,
    /** The data center of the Linkshell. Always `null` if this is not a Cross-world Linkshell, otherwise never `null`. */
    val dataCenter: DataCenter?,
    /** The Linkshell's region. Always `null` if this is not a Cross-world Linkshell, otherwise never `null`. */
    val region: Region?,
    /** The Linkshells members. */
    val members: List<LinkshellMember>,
)
