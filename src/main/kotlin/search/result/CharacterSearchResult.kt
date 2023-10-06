package cloud.drakon.ktlodestone.search.result

data class CharacterSearchResult(
    val avatar: String,
    val id: Int,
    val lang: String,
    val name: String,
    val grandCompany: String?,
    val rank: String?,
    val rankIcon: String?,
    val server: String,
    val dc: String,
)
