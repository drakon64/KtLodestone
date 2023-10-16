package cloud.drakon.ktlodestone.character.search

import cloud.drakon.ktlodestone.character.ActiveClassJob
import cloud.drakon.ktlodestone.character.ClassJob
import cloud.drakon.ktlodestone.character.Guild
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyRank
import cloud.drakon.ktlodestone.character.profile.Clan
import cloud.drakon.ktlodestone.character.profile.Race
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.ktorClient
import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileMaps
import cloud.drakon.ktlodestone.selectors.character.search.CharacterSearchSelectors
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

// To ensure that `nextPage` isn't accessed statically, these functions get their own class
internal class CharacterSearch {
    private var nextPage = true

    suspend fun scrapeCharacterSearch(
        name: String?,
        world: World?,
        dataCenter: DataCenter?,
        classJob: ClassJob?,
        race: Race?,
        clan: Clan?,
        grandCompanies: Set<GrandCompanyName?>?,
        languages: Set<Language>?,
        pages: Byte,
    ) = coroutineScope {
        var page: Byte = 1

        buildList {
            while (page <= pages && nextPage) {
                add(
                    searchLodestoneCharacterPaginated(
                        name,
                        world,
                        dataCenter,
                        classJob,
                        race,
                        clan,
                        grandCompanies,
                        languages,
                        page
                    )
                )

                page++
            }
        }.flatten()
    }

    private suspend fun searchLodestoneCharacterPaginated(
        name: String?,
        world: World?,
        dataCenter: DataCenter?,
        classJob: ClassJob?,
        race: Race?,
        clan: Clan?,
        grandCompanies: Set<GrandCompanyName?>?,
        languages: Set<Language>?,
        page: Byte,
    ): List<CharacterSearchResult> = coroutineScope {
        ktorClient.get("character/") {
            url {
                // We've already encoded this parameter
                if (name != null) encodedParameters.append("q", name.replace(" ", "+"))

                // If [world] is provided, use it as it's more specific, otherwise use [dataCenter]
                if (world != null) {
                    parameters.append("worldname", world.name)
                } else if (dataCenter != null) {
                    parameters.append("worldname", dataCenter.name)
                }

                if (classJob != null) parameters.append("classjob", classJob.name)

                // If both [clan] and [race] or just [clan] are provided, use [clan] as it's more specific, otherwise use [race]
                if ((clan != null && race != null) || clan != null) {
                    parameters.append("race_tribe", clan.name)
                } else if (race != null) {
                    parameters.append("race_tribe", race.name)
                }

                grandCompanies?.forEach {
                    when (it) {
                        GrandCompanyName.MAELSTROM -> parameters.append("gcid", "1")
                        GrandCompanyName.ORDER_OF_THE_TWIN_ADDER -> parameters.append("gcid", "2")
                        GrandCompanyName.IMMORTAL_FLAMES -> parameters.append("gcid", "3")
                        null -> parameters.append("gcid", "0")
                    }
                }

                languages?.forEach {
                    when (it) {
                        Language.JAPANESE -> parameters.append("blog_lang", "ja")
                        Language.ENGLISH -> parameters.append("blog_lang", "en")
                        Language.GERMAN -> parameters.append("blog_lang", "de")
                        Language.FRENCH -> parameters.append("blog_lang", "fr")
                    }
                }

                parameters.append("page", "$page")
            }
        }.let {
            when (it.status.value) {
                200 -> Jsoup.parse(it.body() as String).let {
                    if (it.select(CharacterSearchSelectors.LIST_NEXT_BUTTON)
                            .attr(CharacterSearchSelectors.LIST_NEXT_BUTTON_ATTR) == "javascript:void(0);"
                    ) nextPage = false

                    it.select(CharacterSearchSelectors.ROOT).let {
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
                                        it.select(CharacterSearchSelectors.ENTRY_LANGUAGE)
                                            .text().let {
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
                                    it.select(CharacterSearchSelectors.ENTRY_NAME)
                                        .text()
                                }

                                val grandCompany = async {
                                    it.select(CharacterSearchSelectors.ENTRY_GRAND_COMPANY_RANK)
                                        .first()
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
                                    it.select(CharacterSearchSelectors.ENTRY_FREE_COMPANY_ID)
                                        .first()
                                        ?.let {
                                            val freeCompanyName = async {
                                                it.select(CharacterSearchSelectors.ENTRY_FREE_COMPANY_NAME)
                                                    .text()
                                            }

                                            val freeCompanyId = async {
                                                it.attr(CharacterSearchSelectors.ENTRY_FREE_COMPANY_ID_ATTR)
                                                    .split("/")[3]
                                            }

                                            Guild(
                                                freeCompanyName.await(),
                                                freeCompanyId.await(),
                                                null
                                            )
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
                                    it.select(CharacterSearchSelectors.ENTRY_CHARA_INFO)
                                        .let {
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

                                            val disciple = async {
                                                CharacterProfileMaps.DISCIPLINE_MAP.getValue(
                                                    classJob.await()
                                                )
                                            }

                                            ActiveClassJob(
                                                classJob.await(),
                                                level.await(),
                                                disciple.await()
                                            )
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

                else -> throw LodestoneException()
            }
        }
    }
}
