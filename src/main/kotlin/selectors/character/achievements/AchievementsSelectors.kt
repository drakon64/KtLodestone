package cloud.drakon.ktlodestone.selectors.character.achievements

// https://github.com/xivapi/lodestone-css-selectors/blob/main/profile/achievements.json
internal object AchievementsSelectors {
    const val ROOT = ".ldst__achievement"

    const val ENTRIES_ROOT = ".entry"

    const val ENTRY_NAME = ".entry__activity__txt"
    const val ENTRY_ID = ".entry__achievement"
    const val ENTRY_ID_ATTR = "href"
    const val ENTRY_TIME = ".entry__activity__time > script"
    val ENTRY_TIME_REGEX = """(?<=ldst_strftime\()\d+""".toRegex()

    const val ACHIEVEMENT_POINTS = ".achievement__point"

    const val TOTAL_ACHIEVEMENTS = ".parts__total"
    val TOTAL_ACHIEVEMENTS_REGEX = """\d+""".toRegex()

    const val LIST_NEXT_BUTTON = "ul.btn__pager:nth-child(2) > li:nth-child(4) > a:nth-child(1)"
    const val LIST_NEXT_BUTTON_ATTR = "href"
}
