package cloud.drakon.ktlodestone.character.profile.gearset

data class Item(
    val name: String,
    val dbLink: String,
    val glamour: Glamour?,
    val dye: String?,
    val materia: List<String>?,
    val creatorName: String?,
)
