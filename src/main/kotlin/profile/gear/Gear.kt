package cloud.drakon.ktlodestone.profile.gear

data class Gear(
    val name: String,
    val dbLink: String,
    val glamour: Glamour?,
    val stain: String?,
    val materia: List<String>?,
    val creatorName: String?,
)
