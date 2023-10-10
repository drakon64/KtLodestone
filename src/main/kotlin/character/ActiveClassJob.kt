package cloud.drakon.ktlodestone.character

import cloud.drakon.ktlodestone.character.classjob.ClassJob

/** A Character's active [ClassJob]. */
data class ActiveClassJob(val classJob: ClassJob, val level: Byte)
