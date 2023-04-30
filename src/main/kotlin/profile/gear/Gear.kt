package cloud.drakon.ktlodestone.profile.gear

data class Gear(
    val name: String,
    val dbLink: String,
    val mirageName: String?,
    val mirageDbLink: String?,
    val stain: String?,
    val materia: List<String>?,
    val creatorName: String?
)
