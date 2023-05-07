package cloud.drakon.ktlodestone.profile.attributes

/**
 * A characters attributes.
 * @property strength Affects physical damage dealt by gladiator's arms, marauder's arms, pugilist's arms, lancer's arms, conjurer's arms, thaumaturge's arms, arcanist's arms, dark knight's arms, samurai's arms, reaper's arms, gunbreaker's arms, red mage's arms, astrologian's arms, sage's arms, and blue mage's arms.
 * @property dexterity Affects physical ranged damage and damage dealt by rogue's arms.
 * @property vitality Affects maximum HP.
 * @property intelligence Affects attack magic potency when role is DPS.
 * @property mind Affects healing magic potency. Also affects attack magic potency when role is Healer.
 * @property criticalHitRate Affects the amount of physical and magic damage dealt, as well as HP restored. The higher the value, the higher the frequency with which your hits will be critical/higher the potency of critical hits.
 * @property determination Affects the amount of damage dealt by both physical and magic attacks, as well as the amount of HP restored by healing spells.
 * @property directHitRate Affects the rate at which your physical and magic attacks land direct hits, dealing slightly more damage than normal hits. The higher the value, the higher the frequency with which your hits will be direct. Higher values will also result in greater damage for actions which guarantee direct hits.
 * @property defense Affects the amount of damage taken by physical attacks. The higher the value, the less damage taken.
 * @property magicDefense Affects the amount of damage taken by magic attacks. The higher the value, the less damage taken.
 * @property attackPower Affects amount of damage dealt by physical attacks. The higher the value, the more damage dealt.
 * @property skillSpeed Affects both the casting and recast timers, as well as the damage over time potency for weaponskills and auto-attacks. The higher the value, the shorter the timers/higher the potency.
 * @property attackMagicPotency Affects the amount of damage dealt by magic attacks.
 * @property healingMagicPotency Affects the amount of HP restored via healing magic.
 * @property spellSpeed Affects both the casting and recast timers for spells. The higher the value, the shorter the timers. Also affects a spell's damage over time or healing over time potency.
 * @property tenacity Affects the amount of physical and magic damage dealt and received, as well as HP restored. The higher the value, the more damage dealt, the more HP restored, and the less damage taken. Only applicable when role is Tank.
 * @property piety Affects MP regeneration. Regeneration rate is determined by piety. Only applicable when in battle and role is Healer.
 * @property hp HP
 * @property mp MP/CP/GP
 */
data class ProfileAttributes(
    val strength: Short,
    val dexterity: Short,
    val vitality: Short,
    val intelligence: Short,
    val mind: Short,
    val criticalHitRate: Short,
    val determination: Short,
    val directHitRate: Short,
    val defense: Short,
    val magicDefense: Short,
    val attackPower: Short,
    val skillSpeed: Short,
    val attackMagicPotency: Short,
    val healingMagicPotency: Short,
    val spellSpeed: Short,
    val tenacity: Short,
    val piety: Short,
    val hp: Int,
    val mp: Int?,
)
