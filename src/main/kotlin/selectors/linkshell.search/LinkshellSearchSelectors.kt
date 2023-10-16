package cloud.drakon.ktlodestone.selectors.cwls.search

// https://github.com/xivapi/lodestone-css-selectors/blob/main/search/linkshell.json
internal object LinkshellSearchSelectors {
    const val ROOT = ".ldst__window"

    const val ENTRIES_ROOT = "div.entry"

    const val ENTRY_NAME = ".entry__name"

    const val ENTRY_ID = ".entry__link--line"
    const val ENTRY_ID_ATTR = "href"

    const val ENTRY_WORLD = ".entry__world"

    const val ENTRY_ACTIVE_MEMBERS = ".entry__linkshell__member > div > span"

    const val LIST_NEXT_BUTTON = "ul.btn__pager > li:nth-child(4) > a:nth-child(1)"
    const val LIST_NEXT_BUTTON_ATTR = "href"
}
