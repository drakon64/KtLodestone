package cloud.drakon.ktlodestone.profile.gearset

/**
 * A gear item.
 * @property name The name of the gear item.
 * @property dbLink The URL to the gear item in the Eorzea Database.
 * @property glamour The glamour applied to the gear item.
 * @property stain The dye applied to the gear item.
 * @property materia The materia(s) attached to the gear item.
 * @property creatorName The name of the creator of the gear item.
 * @property hq If the item is high quality.
 */
data class Gear(
    val name: String,
    val dbLink: String,
    val glamour: Glamour?,
    val stain: String?,
    val materia: List<String>?,
    val creatorName: String?,
    val hq: Boolean,
)
