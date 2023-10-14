package cloud.drakon.ktlodestone.character

import cloud.drakon.ktlodestone.character.profile.Discipline

/** A Character's active [ClassJob]. */
data class ActiveClassJob(
    val classJob: ClassJob,
    val level: Byte,
    val discipline: Discipline,
)
