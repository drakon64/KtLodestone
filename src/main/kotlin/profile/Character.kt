package cloud.drakon.ktlodestone.profile

import cloud.drakon.ktlodestone.profile.freecompany.FreeCompany
import cloud.drakon.ktlodestone.profile.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.profile.guardian.Guardian
import cloud.drakon.ktlodestone.profile.pvpteam.PvpTeam
import cloud.drakon.ktlodestone.profile.town.Town

data class Character(
    val activeClassJob: String,
    val activeClassJobLevel: Byte,
    val avatar: String,
    val bio: String,
    val freeCompany: FreeCompany?,
    val grandCompany: GrandCompany?,
    val guardian: Guardian,
    val name: String,
    val nameday: String,
    val portrait: String,
    val pvpTeam: PvpTeam?,
    val race: String,
    val clan: String,
    val gender: String,
    val server: String,
    val dc: String,
    val title: String?,
    val town: Town,
)
