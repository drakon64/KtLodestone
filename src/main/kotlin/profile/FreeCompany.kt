package cloud.drakon.ktlodestone.profile

data class FreeCompany(
    override val name: String,
    override val id: String,
    override val iconLayers: IconLayers,
): Guild
