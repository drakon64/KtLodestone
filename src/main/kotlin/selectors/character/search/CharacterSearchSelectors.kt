package cloud.drakon.ktlodestone.selectors.character.search

internal object CharacterSearchSelectors {
    const val ROOT = ".ldst__window"

    const val ENTRIES_ROOT = "div.entry"

    const val ENTRY_AVATAR = ".entry__chara__face > img"
    const val ENTRY_AVATAR_ATTR = "src"

    const val ENTRY_ID = ".entry__link"
    const val ENTRY_ID_ATTR = "href"

    const val ENTRY_LANGUAGE = ".entry__chara__lang"

    const val ENTRY_NAME = ".entry__name"

    const val ENTRY_GRAND_COMPANY_RANK = ".entry__chara_info > .js__tooltip"
    const val ENTRY_GRAND_COMPANY_RANK_ATTR = "data-tooltip"

    const val ENTRY_WORLD = ".entry__world"
}
