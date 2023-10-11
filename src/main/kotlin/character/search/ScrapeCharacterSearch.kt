package cloud.drakon.ktlodestone.character.search

import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyRank
import cloud.drakon.ktlodestone.selectors.character.search.CharacterSearchSelectors
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

internal suspend fun scrapeCharacterSearch(response: String) = coroutineScope {
    Jsoup.parse(response).select(CharacterSearchSelectors.ROOT).let {
        buildList {
            it.select(CharacterSearchSelectors.ENTRIES_ROOT).forEach {
                val avatar = async {
                    it.select(CharacterSearchSelectors.ENTRY_AVATAR)
                        .attr(CharacterSearchSelectors.ENTRY_AVATAR_ATTR)
                }

                val id = async {
                    it.select(CharacterSearchSelectors.ENTRY_ID)
                        .attr(CharacterSearchSelectors.ENTRY_ID_ATTR)
                        .split("/")[3]
                        .toInt()
                }

                val language = async {
                    buildList {
                        it.select(CharacterSearchSelectors.ENTRY_LANGUAGE).text().let {
                            if (it.contains("JA")) {
                                add(Language.JAPANESE)
                            }

                            if (it.contains("EN")) {
                                add(Language.ENGLISH)
                            }

                            if (it.contains("DE")) {
                                add(Language.GERMAN)
                            }

                            if (it.contains("FR")) {
                                add(Language.FRENCH)
                            }
                        }
                    }
                }

                val name = async {
                    it.select(CharacterSearchSelectors.ENTRY_NAME).text()
                }

                val grandCompany = async {
                    val grandCompanyTooltip = it.select(CharacterSearchSelectors.ENTRY_GRAND_COMPANY_RANK)
                        .attr(CharacterSearchSelectors.ENTRY_GRAND_COMPANY_RANK_ATTR).let {
                            it.ifEmpty { null }
                        }

                    if (grandCompanyTooltip != null) {
                        val grandCompanyName = async {
                            GrandCompanyName.valueOf(
                                grandCompanyTooltip.split("/")[0]
                                    .trim()
                                    .replace(" ", "_")
                                    .uppercase()
                            )
                        }

                        val grandCompanyRank = async {
                            GrandCompanyRank.valueOf(
                                grandCompanyTooltip.split("/")[1]
                                    .trim()
                                    .replace(" ", "_")
                                    .uppercase()
                            )
                        }

                        GrandCompany(
                            grandCompanyName.await(),
                            grandCompanyRank.await()
                        )
                    } else null
                }

                val world = async {
                    World.valueOf(
                        it.select(CharacterSearchSelectors.ENTRY_WORLD)
                            .text()
                            .split("[")[0]
                            .trim()
                    )
                }

                val dataCenter = async {
                    DataCenter.valueOf(
                        it.select(CharacterSearchSelectors.ENTRY_WORLD)
                            .text()
                            .split("[")[1]
                            .replace("]", "")
                    )
                }

                add(
                    CharacterSearchResult(
                        avatar.await(),
                        id.await(),
                        language.await(),
                        name.await(),
                        grandCompany.await(),
                        world.await(),
                        dataCenter.await(),
                    )
                )
            }
        }
    }
}
