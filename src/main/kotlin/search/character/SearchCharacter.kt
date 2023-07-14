package cloud.drakon.ktlodestone.search.character

data class SearchCharacter(
    val avatar: String,
    val id: Int,
    val lang: String,
    val name: String,
    val grandCompany: String?,
    val rank: String?,
    val rankIcon: String?,
    val server: String,
    val dc: String
)
