package cloud.drakon.ktlodestone.linkshell

import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.Region
import cloud.drakon.ktlodestone.world.World

data class LinkshellMember(
    /** The Linkshell members name. */
    val name: String,
    /** The character ID of the Linkshell member. */
    val id: Int,
    /** The Linkshell members avatar. */
    val avatar: String,
    /** The Linkshell members Grand Company. */
    val grandCompany: GrandCompany?,
    /** The Linkshell members rank within the Linkshell. */
    val linkshellRank: String?,
    /** The Linkshell members world. */
    val world: World,
    /** The Linkshell members data center. */
    val dataCenter: DataCenter,
    /** The Linkshell members region. */
    val region: Region,
)
