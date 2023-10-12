package cloud.drakon.ktlodestone.character.search

import cloud.drakon.ktlodestone.character.ActiveClassJob
import cloud.drakon.ktlodestone.character.Guild
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyRank
import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileMaps
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
                    it.select(CharacterSearchSelectors.ENTRY_GRAND_COMPANY_RANK).first()
                        ?.attr(CharacterSearchSelectors.ENTRY_GRAND_COMPANY_RANK_ATTR)
                        ?.let {
                            val grandCompanyName = async {
                                GrandCompanyName.valueOf(
                                    it.split("/")[0]
                                        .trim()
                                        .replace(" ", "_")
                                        .uppercase()
                                )
                            }

                            val grandCompanyRank = async {
                                GrandCompanyRank.valueOf(
                                    it.split("/")[1]
                                        .trim()
                                        .replace(" ", "_")
                                        .uppercase()
                                )
                            }

                            GrandCompany(
                                grandCompanyName.await(),
                                grandCompanyRank.await()
                            )
                        }
                }

                val freeCompany = async {
                    it.select(CharacterSearchSelectors.ENTRY_FREE_COMPANY_ID).first()
                        ?.let {
                            val freeCompanyName = async {
                                it.select(CharacterSearchSelectors.ENTRY_FREE_COMPANY_NAME)
                                    .text()
                            }

                            val freeCompanyId = async {
                                it.attr(CharacterSearchSelectors.ENTRY_FREE_COMPANY_ID_ATTR)
                                    .split("/")[3]
                            }

                            Guild(freeCompanyName.await(), freeCompanyId.await(), null)
                        }
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

                val region = async {
                    CharacterProfileMaps.REGION_MAP.getValue(dataCenter.await())
                }

                val activeClassJob = async {
                    it.select(CharacterSearchSelectors.ENTRY_CHARA_INFO).let {
                        val classJob = async {
                            CharacterProfileMaps.CLASS_JOB_MAP.getValue(
                                it.select(CharacterSearchSelectors.ENTRY_ACTIVE_CLASSJOB)
                                    .attr(CharacterSearchSelectors.ENTRY_ACTIVE_CLASSJOB_ATTR)
                            )
                        }

                        val level = async {
                            it.select(CharacterSearchSelectors.ENTRY_ACTIVE_CLASSJOB_LEVEL)
                                .text()
                                .toByte()
                        }

                        ActiveClassJob(classJob.await(), level.await())
                    }
                }

                add(
                    CharacterSearchResult(
                        avatar.await(),
                        id.await(),
                        language.await(),
                        name.await(),
                        grandCompany.await(),
                        freeCompany.await(),
                        world.await(),
                        dataCenter.await(),
                        region.await(),
                        activeClassJob.await(),
                    )
                )
            }
        }
    }
}
