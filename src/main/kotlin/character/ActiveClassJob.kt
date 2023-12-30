package cloud.drakon.ktlodestone.character

import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileMaps

/** A Character's active [ClassJob]. */
data class ActiveClassJob(
    val classJob: ClassJob,
    val level: Byte,
) {
    val discipline = CharacterProfileMaps.DISCIPLINE_MAP.getValue(classJob)
}
