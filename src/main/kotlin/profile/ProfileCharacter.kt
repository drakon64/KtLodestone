package cloud.drakon.ktlodestone.profile

data class ProfileCharacter(
    val activeClassJob: String,
    val activeClassJobLevel: Byte,
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
    val attributes: Attributes?,
)
