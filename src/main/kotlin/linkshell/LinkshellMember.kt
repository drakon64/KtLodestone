package cloud.drakon.ktlodestone.linkshell

import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.Region
import cloud.drakon.ktlodestone.world.World

data class LinkshellMember(
    val name: String,
    val id: Int,
    val avatar: String,
    val grandCompany: GrandCompany?,
    val linkshellRank: String?,
    val world: World,
    val dataCenter: DataCenter,
    val region: Region,
)
