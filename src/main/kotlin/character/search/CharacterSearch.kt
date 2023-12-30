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
    ): List<CharacterSearchResult> {
        var page: Byte = 1

        return buildList {
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
    ): List<CharacterSearchResult> = ktorClient.get("character/") {
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
                            val avatar = it.select(CharacterSearchSelectors.ENTRY_AVATAR)
                                .attr(CharacterSearchSelectors.ENTRY_AVATAR_ATTR)

                            val id = it.select(CharacterSearchSelectors.ENTRY_ID)
                                .attr(CharacterSearchSelectors.ENTRY_ID_ATTR)
                                .split("/")[3]
                                .toInt()

                            val language = buildList {
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

                            val name = it.select(CharacterSearchSelectors.ENTRY_NAME).text()

                            val grandCompany = it.select(CharacterSearchSelectors.ENTRY_GRAND_COMPANY_RANK)
                                .first()
                                ?.attr(CharacterSearchSelectors.ENTRY_GRAND_COMPANY_RANK_ATTR)
                                ?.let {
                                    GrandCompany(
                                        GrandCompanyName.valueOf(
                                            it.split("/")[0]
                                                .trim()
                                                .replace(" ", "_")
                                                .uppercase()
                                        ),
                                        GrandCompanyRank.valueOf(
                                            it.split("/")[1]
                                                .trim()
                                                .replace(" ", "_")
                                                .uppercase()
                                        )
                                    )
                                }

                            val freeCompany = it.select(CharacterSearchSelectors.ENTRY_FREE_COMPANY_ID)
                                .first()
                                ?.let {
                                    Guild(
                                        it.select(CharacterSearchSelectors.ENTRY_FREE_COMPANY_NAME)
                                            .text(),
                                        it.attr(CharacterSearchSelectors.ENTRY_FREE_COMPANY_ID_ATTR)
                                            .split("/")[3],
                                        null
                                    )
                                }

                            val world = World.valueOf(
                                it.select(CharacterSearchSelectors.ENTRY_WORLD)
                                    .text()
                                    .split("[")[0]
                                    .trim()
                            )

                            val dataCenter = DataCenter.valueOf(
                                it.select(CharacterSearchSelectors.ENTRY_WORLD)
                                    .text()
                                    .split("[")[1]
                                    .replace("]", "")
                            )

                            val region = CharacterProfileMaps.REGION_MAP.getValue(dataCenter)

                            val activeClassJob = it.select(CharacterSearchSelectors.ENTRY_CHARA_INFO)
                                .let {
                                    ActiveClassJob(
                                        CharacterProfileMaps.CLASS_JOB_MAP.getValue(
                                            it.select(CharacterSearchSelectors.ENTRY_ACTIVE_CLASSJOB)
                                                .attr(CharacterSearchSelectors.ENTRY_ACTIVE_CLASSJOB_ATTR)
                                        ),
                                        it.select(CharacterSearchSelectors.ENTRY_ACTIVE_CLASSJOB_LEVEL)
                                            .text()
                                            .toByte()
                                    )
                                }

                            add(
                                CharacterSearchResult(
                                    avatar,
                                    id,
                                    language,
                                    name,
                                    grandCompany,
                                    freeCompany,
                                    world,
                                    dataCenter,
                                    region,
                                    activeClassJob,
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
