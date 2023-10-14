package cloud.drakon.ktlodestone.character.profile

import cloud.drakon.ktlodestone.character.ActiveClassJob

/** A [CharacterProfile]'s Attributes. */
data class Attributes(
    /** Affects physical damage dealt by gladiator's arms, marauder's arms, pugilist's arms, lancer's arms, conjurer's arms, thaumaturge's arms, arcanist's arms, dark knight's arms, samurai's arms, reaper's arms, gunbreaker's arms, red mage's arms, astrologian's arms, sage's arms, and blue mage's arms. */
    val strength: Short,
    /** Affects physical ranged damage and damage dealt by rogue's arms. */
    val dexterity: Short,
    /** Affects maximum HP. */
    val vitality: Short,
    /** Affects attack magic potency when role is DPS. */
    val intelligence: Short,
    /** Affects healing magic potency. Also affects attack magic potency when role is Healer. */
    val mind: Short,
    /** Affects the amount of physical and magic damage dealt, as well as HP restored. The higher the value, the higher the frequency with which your hits will be critical/higher the potency of critical hits. */
    val criticalHitRate: Short,
    /** Affects the amount of damage dealt by both physical and magic attacks, as well as the amount of HP restored by healing spells. */
    val determination: Short,
    /** Affects the rate at which your physical and magic attacks land direct hits, dealing slightly more damage than normal hits. The higher the value, the higher the frequency with which your hits will be direct. Higher values will also result in greater damage for actions which guarantee direct hits. */
    val directHitRate: Short,
    /** Affects the amount of damage taken by physical attacks. The higher the value, the less damage taken. */
    val defense: Short,
    /** Affects the amount of damage taken by magic attacks. The higher the value, the less damage taken. */
    val magicDefense: Short,
    /** Affects amount of damage dealt by physical attacks. The higher the value, the more damage dealt. */
    val attackPower: Short,
    /** Affects both the casting and recast timers, as well as the damage over time potency for weaponskills and auto-attacks. The higher the value, the shorter the timers/higher the potency. */
    val skillSpeed: Short,
    /** Affects the amount of damage dealt by magic attacks. */
    val attackMagicPotency: Short,
    /** Affects the amount of HP restored via healing magic. */
    val healingMagicPotency: Short,
    /** Affects both the casting and recast timers for spells. The higher the value, the shorter the timers. Also affects a spell's damage over time or healing over time potency. */
    val spellSpeed: Short,
    /** Affects the amount of physical and magic damage dealt and received, as well as HP restored. The higher the value, the more damage dealt, the more HP restored, and the less damage taken. Only applicable when role is Tank. */
    val tenacity: Short,
    /** Affects MP regeneration. Regeneration rate is determined by piety. Only applicable when in battle and role is Healer. */
    val piety: Short,
    val hp: Int,
    /** Never `null` if [ActiveClassJob.disciple] is [Disciple.DISCIPLE_OF_THE_HAND]. */
    val cp: Short?,
    /** Never `null` if [ActiveClassJob.disciple] is [Disciple.DISCIPLE_OF_THE_LAND]. */
    val gp: Short?,
)
