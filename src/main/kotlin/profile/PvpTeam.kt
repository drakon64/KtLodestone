package cloud.drakon.ktlodestone.profile

data class PvpTeam(
    override val name: String,
    override val id: String,
    override val iconLayers: IconLayers,
): Guild
