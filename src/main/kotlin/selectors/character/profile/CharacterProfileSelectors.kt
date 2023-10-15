package cloud.drakon.ktlodestone.selectors.character.profile

// https://github.com/xivapi/lodestone-css-selectors/blob/main/profile/character.json
internal object CharacterProfileSelectors {
    const val ACTIVE_CLASSJOB = ".character__class_icon > img:nth-child(1)"
    const val ACTIVE_CLASSJOB_ATTR = "src"
    const val ACTIVE_CLASSJOB_LEVEL = ".character__class__data > p:nth-child(1)"
    val ACTIVE_CLASSJOB_LEVEL_REGEX = """\d+""".toRegex()

    const val CLASSJOB_CLEARFIX = "div.character__level"
    const val CLASSJOB_ENTRIES = "div > ul"
    const val CLASSJOB = "li"
    const val CLASSJOB_ICON = "img"
    const val CLASSJOB_ICON_ATTR = "src"

    const val AVATAR = ".frame__chara__face > img:nth-child(1)"
    const val AVATAR_ATTR = "src"

    const val BIO = ".character__selfintroduction"

    const val FREE_COMPANY =
        ".character__freecompany__name > h4:nth-child(2) > a:nth-child(1)"
    const val FREE_COMPANY_ID_ATTR = "href"
    const val FREE_COMPANY_BOTTOM_ICON_LAYER =
        "div.character__freecompany__crest > div > img:nth-child(1)"
    const val FREE_COMPANY_MIDDLE_ICON_LAYER =
        "div.character__freecompany__crest > div > img:nth-child(2)"
    const val FREE_COMPANY_TOP_ICON_LAYER =
        "div.character__freecompany__crest > div > img:nth-child(3)"
    const val FREE_COMPANY_ICON_LAYER_ATTR = "src"

    const val GRAND_COMPANY =
        "div.character-block:nth-child(4) > div:nth-child(2) > p:nth-child(2)"

    const val GUARDIAN_NAME = "p.character-block__name:nth-child(4)"

    const val NAME = "div.frame__chara__box:nth-child(2) > .frame__chara__name"

    const val NAMEDAY = ".character-block__birth"

    const val PORTRAIT = ".js__image_popup > img:nth-child(1)"
    const val PORTRAIT_ATTR = "src"

    const val PVP_TEAM = ".character__pvpteam__name > h4:nth-child(2) > a:nth-child(1)"
    const val PVP_TEAM_ID_ATTR = "href"
    const val PVP_TEAM_BOTTOM_ICON_LAYER =
        ".character__pvpteam__crest__image img:nth-child(1)"
    const val PVP_TEAM_MIDDLE_ICON_LAYER =
        ".character__pvpteam__crest__image img:nth-child(2)"
    const val PVP_TEAM_TOP_ICON_LAYER =
        ".character__pvpteam__crest__image img:nth-child(3)"
    const val PVP_TEAM_ICON_LAYER_ATTR = "src"

    const val RACE_CLAN_GENDER =
        "div.character-block:nth-child(1) > div:nth-child(2) > p:nth-child(2)"
    val RACE_REGEX = """.*(?=<br>)""".toRegex()
    val CLAN_REGEX = """(?<=<br>\n)[^ /]+""".toRegex()
    val GENDER_REGEX = """[♀|♂]""".toRegex()

    const val WORLD = "p.frame__chara__world"

    const val TITLE = ".frame__chara__title"

    const val TOWN =
        "div.character-block:nth-child(3) > div:nth-child(2) > p:nth-child(2)"
}
