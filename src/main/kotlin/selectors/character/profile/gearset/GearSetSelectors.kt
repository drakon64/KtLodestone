package cloud.drakon.ktlodestone.selectors.character.profile.gearset

internal sealed interface GearSetSelectors {
    val ITEM: String

    val NAME_SELECTOR: String

    val DB_LINK: String
    val DB_LINK_ATTR: String

    val GLAMOUR: String
    val GLAMOUR_DB_LINK: String
    val GLAMOUR_DB_LINK_ATTR: String

    val DYE: String

    val MATERIA_1: String
    val MATERIA_2: String
    val MATERIA_3: String
    val MATERIA_4: String
    val MATERIA_5: String
    val MATERIA_REGEX: Regex

    val CREATOR_NAME: String
}
