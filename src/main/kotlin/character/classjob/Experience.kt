package cloud.drakon.ktlodestone.character.classjob

data class Experience(
    /** A characters current experience in a class/job. */
    val currentExp: Int,
    /** How much experience a character needs to reach the next level in a class/job. */
    val expToNextLevel: Int,
)
