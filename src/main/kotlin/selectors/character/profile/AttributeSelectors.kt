package cloud.drakon.ktlodestone.selectors.character.profile

// https://github.com/xivapi/lodestone-css-selectors/blob/main/profile/attributes.json
internal object AttributeSelectors {
    const val STRENGTH = "table.character__param__list:nth-child(2) tr:nth-child(1) > td:nth-child(2)"
    const val DEXTERITY = "table.character__param__list:nth-child(2) tr:nth-child(2) > td:nth-child(2)"
    const val VITALITY = "table.character__param__list:nth-child(2) tr:nth-child(3) > td:nth-child(2)"
    const val INTELLIGENCE = "table.character__param__list:nth-child(2) tr:nth-child(4) > td:nth-child(2)"
    const val MIND = "table.character__param__list:nth-child(2) tr:nth-child(5) > td:nth-child(2)"

    const val CRITICAL_HIT_RATE = "table.character__param__list:nth-child(4) tr:nth-child(1) > td:nth-child(2)"
    const val DETERMINATION = "table.character__param__list:nth-child(4) tr:nth-child(2) > td:nth-child(2)"
    const val DIRECT_HIT_RATE = "table.character__param__list:nth-child(4) tr:nth-child(3) > td:nth-child(2)"

    const val DEFENSE = "table.character__param__list:nth-child(6) tr:nth-child(1) > td:nth-child(2)"
    const val MAGIC_DEFENSE = "table.character__param__list:nth-child(6) tr:nth-child(2) > td:nth-child(2)"

    const val ATTACK_POWER = "table.character__param__list:nth-child(8) tr:nth-child(1) > td:nth-child(2)"
    const val SKILL_SPEED = "table.character__param__list:nth-child(8) tr:nth-child(2) > td:nth-child(2)"

    const val ATTACK_MAGIC_POTENCY = "table.character__param__list:nth-child(10) tr:nth-child(1) > td:nth-child(2)"
    const val HEALING_MAGIC_POTENCY = "table.character__param__list:nth-child(10) tr:nth-child(2) > td:nth-child(2)"
    const val SPELL_SPEED = "table.character__param__list:nth-child(10) tr:nth-child(3) > td:nth-child(2)"

    const val TENACITY = "table.character__param__list:nth-child(12) tr:nth-child(1) > td:nth-child(2)"
    const val PIETY = "table.character__param__list:nth-child(12) tr:nth-child(2) > td:nth-child(2)"

    const val HP = ".character__param > ul:nth-child(1) > li:nth-child(1) > div:nth-child(1) > span:nth-child(2)"

    const val CP_GP = ".character__param > ul:nth-child(1) > li:nth-child(2) > div:nth-child(1) > span:nth-child(2)"
}
