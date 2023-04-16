package cloud.drakon.ktlodestone.profile

data class Guild(val name: String, val id: String, val iconLayers: IconLayers)

typealias FreeCompany = Guild
typealias PvpTeam = Guild
