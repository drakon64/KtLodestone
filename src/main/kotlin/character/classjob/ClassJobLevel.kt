package cloud.drakon.ktlodestone.character.classjob

import cloud.drakon.ktlodestone.character.ClassJob

data class ClassJobLevel(
    val unlockState: ClassJob,
    val level: Byte,
    val experience: Experience?
)
