package cloud.drakon.ktlodestone.character.search

import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World

data class CharacterSearchResult(
    val avatar: String,
    val id: Int,
    val language: List<Language>,
    val name: String,
    val grandCompany: GrandCompany?,
    val world: World,
    val dataCenter: DataCenter,
//    val activeClassJob: ActiveClassJob
)
