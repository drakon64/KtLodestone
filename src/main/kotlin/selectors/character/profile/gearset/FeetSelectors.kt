package cloud.drakon.ktlodestone.selectors.character.profile.gearset

// https://github.com/xivapi/lodestone-css-selectors/blob/main/profile/gearset.json
object FeetSelectors: GearSetSelectors {
    override val ITEM = ".icon-c--7 > .db-tooltip"

    override val NAME_SELECTOR = ".icon-c--7 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > h2:nth-child(2)"

    override val DB_LINK = ".icon-c--7 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1)"
    override val DB_LINK_ATTR = "href"

    override val GLAMOUR = ".icon-c--7 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3)"
    override val GLAMOUR_NAME = "p:nth-child(2)" // Child of GLAMOUR
    override val GLAMOUR_DB_LINK = "a:nth-child(1)" // Child of GLAMOUR_NAME
    override val GLAMOUR_DB_LINK_ATTR = "href"

    override val DYE = ".icon-c--7 > div:nth-child(4) > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > div:nth-child(3) > div:nth-child(1) > a:nth-child(1)"

    override val MATERIA_1 = ".icon-c--7 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > ul:nth-of-type(2) > li:nth-child(1) > div:nth-child(2)"
    override val MATERIA_2 = ".icon-c--7 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > ul:nth-of-type(2) > li:nth-child(2) > div:nth-child(2)"
    override val MATERIA_3 = ".icon-c--7 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > ul:nth-of-type(2) > li:nth-child(3) > div:nth-child(2)"
    override val MATERIA_4 = ".icon-c--7 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > ul:nth-of-type(2) > li:nth-child(4) > div:nth-child(2)"
    override val MATERIA_5 = ".icon-c--7 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > ul:nth-of-type(2) > li:nth-child(5) > div:nth-child(2)"
    override val MATERIA_REGEX = """.*(?=\n<br>)""".toRegex()

    override val CREATOR_NAME = ".icon-c--7 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(7) > div:nth-child(1) > a:nth-child(1)"
}
