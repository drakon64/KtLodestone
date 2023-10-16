package cloud.drakon.ktlodestone.selectors.character.profile

internal class GearSetSelectors(val icon: Byte) {
    companion object {
        const val NAME_SELECTOR = "div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > h2:nth-child(2)"

        const val DB_LINK = "div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1)"
        const val DB_LINK_ATTR = "href"

        const val GLAMOUR = "div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3)"
        const val GLAMOUR_NAME = "p:nth-child(2)"
        const val GLAMOUR_DB_LINK = "a:nth-child(1)"
        const val GLAMOUR_DB_LINK_ATTR = "href"

        val MATERIA_REGEX = """.*(?=\n<br>)""".toRegex()

        const val CREATOR_NAME = "div:nth-child(1) > div:nth-child(1) > div:nth-child(7) > div:nth-child(1) > a:nth-child(1)"
    }

    val ITEM = ".icon-c--$icon > .db-tooltip"

    val DYE = ".icon-c--$icon > div:nth-child(4) > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > div:nth-child(3) > div:nth-child(1) > a:nth-child(1)"

    // Materia selectors have to be from the root level
    val MATERIA_1 = ".icon-c--$icon > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > ul:nth-of-type(2) > li:nth-child(1) > div:nth-child(2)"
    val MATERIA_2 = ".icon-c--$icon > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > ul:nth-of-type(2) > li:nth-child(2) > div:nth-child(2)"
    val MATERIA_3 = ".icon-c--$icon > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > ul:nth-of-type(2) > li:nth-child(3) > div:nth-child(2)"
    val MATERIA_4 = ".icon-c--$icon > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > ul:nth-of-type(2) > li:nth-child(4) > div:nth-child(2)"
    val MATERIA_5 = ".icon-c--$icon > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > ul:nth-of-type(2) > li:nth-child(5) > div:nth-child(2)"
}
