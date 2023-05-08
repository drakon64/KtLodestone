package cloud.drakon.ktlodestone.profile.achievements

data class ProfileAchievements(
    val achievements: List<Achievement>,
    val total: Short,
    val points: Short,
)
