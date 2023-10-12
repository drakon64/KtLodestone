package cloud.drakon.ktlodestone.selectors.character.profile.gearset

// https://github.com/xivapi/lodestone-css-selectors/blob/main/profile/gearset.json
object MainHandSelectors {
    const val NAME_SELECTOR = ".icon-c--0 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > h2:nth-child(2)"

    const val DB_LINK = ".icon-c--0 > .db-tooltip > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1)"
    const val DB_LINK_ATTR = "href"
}
