package cloud.drakon.ktlodestone.profile

import cloud.drakon.ktlodestone.profile.freecompany.FreeCompany
import cloud.drakon.ktlodestone.profile.guardiandeity.GuardianDeity
import cloud.drakon.ktlodestone.profile.pvpteam.PvpTeam
import cloud.drakon.ktlodestone.profile.town.Town

class Character(
    val activeClassJob: String,
    val activeClassJobLevel: Byte,
    val avatar: String,
    val bio: String,
    val freeCompany: FreeCompany?,
    val grandCompany: String?,
    val guardianDeity: GuardianDeity,
    val name: String,
    val nameday: String,
    val portrait: String,
    val pvpTeam: PvpTeam?,
    val raceClanGender: String,
    val server: String,
    val title: String?,
    val town: Town,
)
