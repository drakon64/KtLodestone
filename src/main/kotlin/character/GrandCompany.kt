package cloud.drakon.ktlodestone.character

import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyRank

/** A [Character]'s Grand Company. */
data class GrandCompany(val name: GrandCompanyName, val rank: GrandCompanyRank)
