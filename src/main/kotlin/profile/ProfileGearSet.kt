package cloud.drakon.ktlodestone.profile

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
