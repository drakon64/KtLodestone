package cloud.drakon.ktlodestone.linkshell

import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.Region

/** A Linkshell. */
data class Linkshell(
    /** The name of the Linkshell. */
    val name: String,
    /** When the Cross-world Linkshell was formed. Always `null` if this is not a Cross-world Linkshell, otherwise never `null`. */
    val formed: Long?,
    /** The data center of the Linkshell. Always `null` if this is not a Cross-world Linkshell, otherwise never `null`. */
    val dataCenter: DataCenter?,
    /** The Linkshell's region. Always `null` if this is not a Cross-world Linkshell, otherwise never `null`. */
    val region: Region?,
    /** How many active members the Linkshell has. */
    val activeMembers: Short,
    /** The Linkshells members. */
    val members: List<LinkshellMember>,
)
