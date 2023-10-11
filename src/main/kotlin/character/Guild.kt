package cloud.drakon.ktlodestone.character

import cloud.drakon.ktlodestone.character.profile.CharacterProfile
import cloud.drakon.ktlodestone.iconlayers.IconLayers

/** A character's [FreeCompany] or [PvpTeam]. */
data class Guild(val name: String, val id: String, val iconLayers: IconLayers?)

/** A [CharacterProfile]'s Free Company. */
typealias FreeCompany = Guild

/** A [CharacterProfile]'s PvP Team. */
typealias PvpTeam = Guild
