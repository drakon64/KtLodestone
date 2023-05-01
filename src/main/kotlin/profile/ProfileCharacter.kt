package cloud.drakon.ktlodestone.profile

import cloud.drakon.ktlodestone.profile.guild.Guild

data class ProfileCharacter(
    val activeClassJob: ActiveClassJob,
    val avatar: String,
    val bio: String,
    val freeCompany: Guild?,
    val grandCompany: GrandCompany?,
    val guardian: Guardian,
    val name: String,
    val nameday: String,
    val portrait: String,
    val pvpTeam: Guild?,
    val race: String,
    val clan: String,
    val gender: String,
    val server: String,
    val dc: String,
    val title: String?,
    val town: Town,
)
