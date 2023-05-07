package cloud.drakon.ktlodestone.profile.character

/**
 * The Grand Company that a character belongs to.
 * @property name The name of the Grand Company.
 * @property rank The rank of the character in the Grand Company.
 * @property icon The URL to the icon of the characters rank in the Grand Company.
 */
data class GrandCompany(val name: String, val rank: String, val icon: String)
