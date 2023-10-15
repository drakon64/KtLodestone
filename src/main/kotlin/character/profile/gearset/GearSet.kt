package cloud.drakon.ktlodestone.character.profile.gearset

/** Items currently equipped by a character. */
data class GearSet(
    val mainHand: Item,
    val offHand: Item?,
    val head: Item?,
    val body: Item?,
    val hands: Item?,
    val legs: Item?,
    val feet: Item?,
    val earrings: Item?,
    val necklace: Item?,
    val bracelets: Item?,
    val ring1: Item?,
    val ring2: Item?,
    val soulCrystal: Item?,
)
