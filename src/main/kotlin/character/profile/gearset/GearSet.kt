package cloud.drakon.ktlodestone.character.profile.gearset

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
    val rings: List<Item?>?,
    val soulCrystal: Item?,
)
