package cloud.drakon.ktlodestone.profile.classjob

/**
 * The levels that a character has in all jobs/classes and unique duties.
 * @property bozja The Resistance Rank of a character.
 * @property eureka The Elemental Level of a character.
 * @property paladin The level of the Paladin/Gladiator job/class of a character.
 * @property warrior The level of the Warrior/Marauder job/class of a character.
 * @property darkKnight The level of the Dark Knight job of a character.
 * @property gunbreaker The level of the Gunbreaker job of a character.
 * @property whiteMage The level of the White Mage/Conjurer job/class of a character.
 * @property scholar The level of the Scholar/Arcanist job/class of a character.
 * @property astrologian The level of the Astrologian job of a character.
 * @property sage The level of the Sage job of a character.
 * @property monk The level of the Monk/Pugilist job/class of a character.
 * @property dragoon The level of the Dragoon/Lancer job/class of a character.
 * @property ninja The level of the Ninja/Rogue job of a character.
 * @property samurai The level of the Samurai job of a character.
 * @property reaper The level of the Reaper job of a character.
 * @property bard The level of the Bard/Archer job/class of a character.
 * @property machinist The level of the Machinist job of a character.
 * @property dancer The level of the Dancer job of a character.
 * @property blackMage The level of the Black Mage/Thaumaturge job/class of a character.
 * @property summoner The level of the Summer/Arcanist job/class of a character.
 * @property redMage The level of the Red Mage job of a character.
 * @property blueMage The level of the Blue Mage limited job of a character.
 * @property carpenter The level of the Carpenter class of a character.
 * @property blacksmith The level of the Blacksmith class of a character.
 * @property armorer The level of the Armorer class of a character.
 * @property goldsmith The level of the Goldsmith class of a character.
 * @property leatherworker The level of the Leatherworker class of a character.
 * @property weaver The level of the Weaver class of a character.
 * @property alchemist The level of the Alchemist class of a character.
 * @property culinarian The level of the Culinarian class of a character.
 * @property miner The level of the Miner class of a character.
 * @property botanist The level of the Botanist class of a character.
 * @property fisher The level of the Fisher class of a character.
 */
data class ProfileClassJob(
    val bozja: UniqueDutyLevel?,
    val eureka: UniqueDutyLevel?,
    val paladin: ClassJobLevel?,
    val warrior: ClassJobLevel?,
    val darkKnight: ClassJobLevel?,
    val gunbreaker: ClassJobLevel?,
    val whiteMage: ClassJobLevel?,
    val scholar: ClassJobLevel?,
    val astrologian: ClassJobLevel?,
    val sage: ClassJobLevel?,
    val monk: ClassJobLevel?,
    val dragoon: ClassJobLevel?,
    val ninja: ClassJobLevel?,
    val samurai: ClassJobLevel?,
    val reaper: ClassJobLevel?,
    val bard: ClassJobLevel?,
    val machinist: ClassJobLevel?,
    val dancer: ClassJobLevel?,
    val blackMage: ClassJobLevel?,
    val summoner: ClassJobLevel?,
    val redMage: ClassJobLevel?,
    val blueMage: ClassJobLevel?,
    val carpenter: ClassJobLevel?,
    val blacksmith: ClassJobLevel?,
    val armorer: ClassJobLevel?,
    val goldsmith: ClassJobLevel?,
    val leatherworker: ClassJobLevel?,
    val weaver: ClassJobLevel?,
    val alchemist: ClassJobLevel?,
    val culinarian: ClassJobLevel?,
    val miner: ClassJobLevel?,
    val botanist: ClassJobLevel?,
    val fisher: ClassJobLevel?,
)
