package cloud.drakon.ktlodestone.character.search

import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.character.Guild
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World

data class CharacterSearchResult(
    /** The character's avatar. */
    val avatar: String,
    /** The character's ID. */
    val id: Int,
    /** The character's list of selected languages. */
    val languages: List<Language>,
    /** The character's name. */
    val name: String,
    /** The character's Grand Company. */
    val grandCompany: GrandCompany?,
    /** The Character's Free Company. */
    val freeCompany: Guild?,
    /** The character's world. */
    val world: World,
    /** The character's data center. */
    val dataCenter: DataCenter,
//    val activeClassJob: ActiveClassJob
)
