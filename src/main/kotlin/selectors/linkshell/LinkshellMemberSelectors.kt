package cloud.drakon.ktlodestone.selectors.linkshell

// https://github.com/xivapi/lodestone-css-selectors/blob/main/cwls/members.json
object LinkshellMemberSelectors {
    const val ROOT = ".ldst__window"

    const val ENTRY_ROOT = "div.entry"

    const val ENTRY_AVATAR = ".entry__chara__face img"
    const val ENTRY_AVATAR_ATTR = "src"

    const val ENTRY_ID = ".entry__link"
    const val ENTRY_ID_ATTR = "href"
    val ENTRY_ID_REGEX = """\d+""".toRegex()

    const val ENTRY_NAME = ".entry__name"

    const val ENTRY_RANK = ".entry__chara_info > .js__tooltip"
    const val ENTRY_RANK_ATTR = "data-tooltip"

    const val ENTRY_LINKSHELL_RANK = ".entry__chara_info__linkshell > span"

    const val ENTRY_WORLD = ".entry__world"

    const val LIST_NEXT_BUTTON = "ul.btn__pager:nth-child(5) > li:nth-child(4) > a:nth-child(1)"
    const val LIST_NEXT_BUTTON_ATTR = "href"
}
