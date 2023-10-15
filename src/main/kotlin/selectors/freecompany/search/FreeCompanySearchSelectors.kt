package cloud.drakon.ktlodestone.selectors.freecompany.search

internal object FreeCompanySearchSelectors {
    const val ROOT = ".ldst__window"

    const val ENTRIES_ROOT = "div.entry"

    const val ENTRY_BOTTOM_CREST_LAYER = ".entry__freecompany__crest__image > img:nth-child(1)"
    const val ENTRY_MIDDLE_CREST_LAYER = ".entry__freecompany__crest__image > img:nth-child(2)"
    const val ENTRY_TOP_CREST_LAYER = ".entry__freecompany__crest__image > img:nth-child(3)"
    const val ENTRY_CREST_LAYER_ATTR = "src"

    const val ENTRY_ID = ".entry__block"
    const val ENTRY_ID_ATTR = "href"

    const val ENTRY_NAME = ".entry__name"

    const val ENTRY_WORLD = ".entry__world:nth-child(3)"

    const val ENTRY_ACTIVE = "li.entry__freecompany__fc-active:nth-child(4)"

    const val ENTRY_ACTIVE_MEMBERS = ".entry__freecompany__fc-member"

    const val ENTRY_RECRUITMENT_OPEN = "li.entry__freecompany__fc-active:nth-child(5)"

    const val ENTRY_ESTATE_BUILT = ".entry__freecompany__fc-housing"

    const val ENTRY_FORMED = ".entry__freecompany__fc-day > script"
    val ENTRY_FORMED_REGEX = """(?<=ldst_strftime\()\d+""".toRegex()

    const val LIST_NEXT_BUTTON = "ul.btn__pager > li:nth-child(4) > a:nth-child(1)"
    const val LIST_NEXT_BUTTON_ATTR = "href"
}
