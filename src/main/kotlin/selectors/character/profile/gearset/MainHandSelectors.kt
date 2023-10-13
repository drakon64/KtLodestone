package cloud.drakon.ktlodestone.selectors.character.profile.gearset

// https://github.com/xivapi/lodestone-css-selectors/blob/main/profile/gearset.json
object MainHandSelectors {
    const val NAME_SELECTOR = ".icon-c--0 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > h2:nth-child(2)"

    const val DB_LINK = ".icon-c--0 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1)"
    const val DB_LINK_ATTR = "href"

    const val GLAMOUR = ".icon-c--0 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > p:nth-child(2)"
    const val GLAMOUR_DB_LINK = ".icon-c--0 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > p:nth-child(2) > a:nth-child(1)"
    const val GLAMOUR_DB_LINK_ATTR = "href"

    const val DYE = ".icon-c--0 > div:nth-child(4) > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > div:nth-child(3) > div:nth-child(1) > a:nth-child(1)"

    const val MATERIA_1 = ".icon-c--0 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > ul:nth-of-type(2) > li:nth-child(1) > div:nth-child(2)"
    val MATERIA_REGEX = """.*(?=\n<br>)""".toRegex()

    const val CREATOR_NAME = ".icon-c--0 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(7) > div:nth-child(1) > a:nth-child(1)"
}