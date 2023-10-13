package cloud.drakon.ktlodestone.selectors.character.profile.gearset

// https://github.com/xivapi/lodestone-css-selectors/blob/main/profile/gearset.json
object SoulCrystalSelectors : GearSetSelectors {
    override val ITEM = ".icon-c--13 > .db-tooltip"

    override val NAME_SELECTOR = "div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > h2:nth-child(2)"

    override val DB_LINK = "div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1)"
    override val DB_LINK_ATTR = "href"

    // Soul Crystal's can't have a glamour
    override val GLAMOUR = ""
    override val GLAMOUR_NAME = ""
    override val GLAMOUR_DB_LINK = ""
    override val GLAMOUR_DB_LINK_ATTR = ""

    // Soul Crystal's can't be dyed
    override val DYE = ""

    // Soul Crystal's cannot have materia affixed
    override val MATERIA_1 = ""
    override val MATERIA_2 = ""
    override val MATERIA_3 = ""
    override val MATERIA_4 = ""
    override val MATERIA_5 = ""
    override val MATERIA_REGEX = """.*(?=\n<br>)""".toRegex()

    // Soul Crystal's cannot be crafted
    override val CREATOR_NAME = ""
}
