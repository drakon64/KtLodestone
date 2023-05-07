package cloud.drakon.ktlodestone.profile.gearset

/**
 * The gear set equipped by a character.
 * @property mainHand The characters equipped main hand.
 * @property offHand The characters equipped off hand.
 * @property head The characters equipped head gear.
 * @property body The characters equipped body gear.
 * @property hands The characters equipped hands gear.
 * @property legs The characters equipped leg gear.
 * @property feet The characters equipped feet gear.
 * @property earrings The characters equipped earrings.
 * @property necklace The characters equipped necklace.
 * @property bracelets The characters equipped bracelet.
 * @property ring The characters equipped rings.
 * @property soulCrystal The characters equipped soul crystal.
 */
data class ProfileGearSet(
    val mainHand: Gear,
    val offHand: Gear?,
    val head: Gear?,
    val body: Gear?,
    val hands: Gear?,
    val legs: Gear?,
    val feet: Gear?,
    val earrings: Gear?,
    val necklace: Gear?,
    val bracelets: Gear?,
    val ring: List<Gear?>?,
    val soulCrystal: String?,
)
