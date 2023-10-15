package cloud.drakon.ktlodestone.selectors.character.classjob

// https://github.com/xivapi/lodestone-css-selectors/blob/main/profile/classjob.json
internal object ClassJobSelectors {
    const val PALADIN_LEVEL = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(2)"
    const val PALADIN_UNLOCK_STATE = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(3)"
    const val PALADIN_EXP = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(4)"

    const val WARRIOR_LEVEL = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(2)"
    const val WARRIOR_UNLOCK_STATE = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(3)"
    const val WARRIOR_EXP = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(4)"

    const val DARK_KNIGHT_LEVEL = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(2)"
    const val DARK_KNIGHT_UNLOCK_STATE = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(3)"
    const val DARK_KNIGHT_EXP = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(4)"

    const val GUNBREAKER_LEVEL = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(2)"
    const val GUNBREAKER_UNLOCK_STATE = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(3)"
    const val GUNBREAKER_EXP = ".character__content > div:nth-child(2) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(4)"

    const val WHITE_MAGE_LEVEL = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(2)"
    const val WHITE_MAGE_UNLOCK_STATE = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(3)"
    const val WHITE_MAGE_EXP = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(4)"

    const val SCHOLAR_LEVEL = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(2)"
    const val SCHOLAR_UNLOCK_STATE = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(3)"
    const val SCHOLAR_EXP = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(4)"

    const val ASTROLOGIAN_LEVEL = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(2)"
    const val ASTROLOGIAN_UNLOCK_STATE = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(3)"
    const val ASTROLOGIAN_EXP = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(4)"

    const val SAGE_LEVEL = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(2)"
    const val SAGE_UNLOCK_STATE = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(3)"
    const val SAGE_EXP = ".character__content > div:nth-child(2) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(4)"

    const val MONK_LEVEL = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(2)"
    const val MONK_UNLOCK_STATE = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(3)"
    const val MONK_EXP = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(4)"

    const val DRAGOON_LEVEL = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(2)"
    const val DRAGOON_UNLOCK_STATE = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(3)"
    const val DRAGOON_EXP = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(4)"

    const val NINJA_LEVEL = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(2)"
    const val NINJA_UNLOCK_STATE = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(3)"
    const val NINJA_EXP = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(4)"

    const val SAMURAI_LEVEL = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(2)"
    const val SAMURAI_UNLOCK_STATE = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(3)"
    const val SAMURAI_EXP = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(4)"

    const val REAPER_LEVEL = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(5) > div:nth-child(2)"
    const val REAPER_UNLOCK_STATE = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(5) > div:nth-child(3)"
    const val REAPER_EXP = "div.clearfix:nth-child(3) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(5) > div:nth-child(4)"

    const val BARD_LEVEL = "div.clearfix:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(2)"
    const val BARD_UNLOCK_STATE = "div.clearfix:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(3)"
    const val BARD_EXP = "div.clearfix:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(4)"

    const val MACHINIST_LEVEL = "div.clearfix:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(2)"
    const val MACHINIST_UNLOCK_STATE = "div.clearfix:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(3)"
    const val MACHINIST_EXP = "div.clearfix:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(4)"

    const val DANCER_LEVEL = "div.clearfix:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(2)"
    const val DANCER_UNLOCK_STATE = "div.clearfix:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(3)"
    const val DANCER_EXP = "div.clearfix:nth-child(3) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(4)"

    const val BLACK_MAGE_LEVEL = "ul.character__job:nth-child(4) > li:nth-child(1) > div:nth-child(2)"
    const val BLACK_MAGE_UNLOCK_STATE = "ul.character__job:nth-child(4) > li:nth-child(1) > div:nth-child(3)"
    const val BLACK_MAGE_EXP = "ul.character__job:nth-child(4) > li:nth-child(1) > div:nth-child(4)"

    const val SUMMONER_LEVEL = "ul.character__job:nth-child(4) > li:nth-child(2) > div:nth-child(2)"
    const val SUMMONER_UNLOCK_STATE = "ul.character__job:nth-child(4) > li:nth-child(2) > div:nth-child(3)"
    const val SUMMONER_EXP = "ul.character__job:nth-child(4) > li:nth-child(2) > div:nth-child(4)"

    const val RED_MAGE_LEVEL = "ul.character__job:nth-child(4) > li:nth-child(3) > div:nth-child(2)"
    const val RED_MAGE_UNLOCK_STATE = "ul.character__job:nth-child(4) > li:nth-child(3) > div:nth-child(3)"
    const val RED_MAGE_EXP = "ul.character__job:nth-child(4) > li:nth-child(3) > div:nth-child(4)"

    const val BLUE_MAGE_LEVEL = "ul.character__job:nth-child(4) > li:nth-child(4) > div:nth-child(2)"
    const val BLUE_MAGE_UNLOCK_STATE = "ul.character__job:nth-child(4) > li:nth-child(4) > div:nth-child(3)"
    const val BLUE_MAGE_EXP = "ul.character__job:nth-child(4) > li:nth-child(4) > div:nth-child(4)"

    const val CARPENTER_LEVEL = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(2)"
    const val CARPENTER_UNLOCK_STATE = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(3)"
    const val CARPENTER_EXP = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(4)"

    const val BLACKSMITH_LEVEL = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(2)"
    const val BLACKSMITH_UNLOCK_STATE = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(3)"
    const val BLACKSMITH_EXP = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(4)"

    const val ARMORER_LEVEL = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(2)"
    const val ARMORER_UNLOCK_STATE = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(3)"
    const val ARMORER_EXP = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(4)"

    const val GOLDSMITH_LEVEL = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(2)"
    const val GOLDSMITH_UNLOCK_STATE = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(3)"
    const val GOLDSMITH_EXP = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(4) > div:nth-child(4)"

    const val LEATHERWORKER_LEVEL = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(5) > div:nth-child(2)"
    const val LEATHERWORKER_UNLOCK_STATE = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(5) > div:nth-child(3)"
    const val LEATHERWORKER_EXP = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(5) > div:nth-child(4)"

    const val WEAVER_LEVEL = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(6) > div:nth-child(2)"
    const val WEAVER_UNLOCK_STATE = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(6) > div:nth-child(3)"
    const val WEAVER_EXP = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(6) > div:nth-child(4)"

    const val ALCHEMIST_LEVEL = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(7) > div:nth-child(2)"
    const val ALCHEMIST_UNLOCK_STATE = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(7) > div:nth-child(3)"
    const val ALCHEMIST_EXP = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(7) > div:nth-child(4)"

    const val CULINARIAN_LEVEL = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(8) > div:nth-child(2)"
    const val CULINARIAN_UNLOCK_STATE = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(8) > div:nth-child(3)"
    const val CULINARIAN_EXP = "div.clearfix:nth-child(5) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(8) > div:nth-child(4)"

    const val MINER_LEVEL = "div.clearfix:nth-child(5) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(2)"
    const val MINER_UNLOCK_STATE = "div.clearfix:nth-child(5) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(3)"
    const val MINER_EXP = "div.clearfix:nth-child(5) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(4)"

    const val BOTANIST_LEVEL = "div.clearfix:nth-child(5) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(2)"
    const val BOTANIST_UNLOCK_STATE = "div.clearfix:nth-child(5) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(3)"
    const val BOTANIST_EXP = "div.clearfix:nth-child(5) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(4)"

    const val FISHER_LEVEL = "div.clearfix:nth-child(5) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(2)"
    const val FISHER_UNLOCK_STATE = "div.clearfix:nth-child(5) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(3)"
    const val FISHER_EXP = "div.clearfix:nth-child(5) > div:nth-child(2) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(4)"
}
