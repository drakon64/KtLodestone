package cloud.drakon.ktlodestone.freecompany.search

import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.iconlayers.IconLayers
import cloud.drakon.ktlodestone.ktorClient
import cloud.drakon.ktlodestone.search.ActiveMembers
import cloud.drakon.ktlodestone.selectors.freecompany.search.FreeCompanySearchMaps
import cloud.drakon.ktlodestone.selectors.freecompany.search.FreeCompanySearchSelectors
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

// To ensure that `nextPage` isn't accessed statically, these functions get their own class
internal class FreeCompanySearch {
    private var nextPage = true

    suspend fun scrapeFreeCompanySearch(
        name: String?,
        communityFinder: Boolean,
        world: World?,
        dataCenter: DataCenter?,
        activeMembers: ActiveMembers?,
        focus: Set<Focus?>?,
        seeking: Set<Seeking?>?,
        active: Active?,
        recruitment: Recruitment?,
        housing: Housing?,
        grandCompany: Set<GrandCompanyName>?,
        pages: Byte,
    ) = coroutineScope {
        var page: Byte = 1

        buildList {
            while (page <= pages && nextPage) {
                add(
                    searchLodestoneFreeCompanySearchPaginated(
                        name,
                        communityFinder,
                        world,
                        dataCenter,
                        activeMembers,
                        focus,
                        seeking,
                        active,
                        recruitment,
                        housing,
                        grandCompany,
                        pages,
                    )
                )

                page++
            }
        }.flatten()
    }

    private suspend fun searchLodestoneFreeCompanySearchPaginated(
        name: String?,
        communityFinder: Boolean,
        world: World?,
        dataCenter: DataCenter?,
        activeMembers: ActiveMembers?,
        focus: Set<Focus?>?,
        seeking: Set<Seeking?>?,
        active: Active?,
        recruitment: Recruitment?,
        housing: Housing?,
        grandCompany: Set<GrandCompanyName>?,
        page: Byte,
    ): List<FreeCompanySearchResult> = coroutineScope {
        ktorClient.get("freecompany/") {
            url {
                // We've already encoded this parameter
                if (name != null) encodedParameters.append("q", name.replace(" ", "+"))

                if (communityFinder) parameters.append("cf_public", "1")

                // If both [world] and [dataCenter] or just [world] are provided, use [world] as it's more specific, otherwise use [dataCenter]
                if ((world != null && dataCenter != null) || world != null) {
                    parameters.append("worldname", world.name)
                } else if (dataCenter != null) {
                    parameters.append("worldname", "_dc_${dataCenter.name}")
                }

                if (activeMembers != null) parameters.append("character_count", "$activeMembers")

                focus?.forEach {
                    when (it) {
                        Focus.ROLE_PLAYING -> parameters.append("activities", "0")
                        Focus.LEVELING -> parameters.append("activities", "1")
                        Focus.CASUAL -> parameters.append("activities", "2")
                        Focus.HARDCORE -> parameters.append("activities", "3")
                        Focus.DUNGEONS -> parameters.append("activities", "6")
                        Focus.GUILDHESTS -> parameters.append("activities", "4")
                        Focus.TRIALS -> parameters.append("activities", "5")
                        Focus.RAIDS -> parameters.append("activities", "7")
                        Focus.PVP -> parameters.append("activities", "8")
                        null -> parameters.append("activities", "-1")
                    }
                }

                seeking?.forEach {
                    when (it) {
                        Seeking.TANK -> parameters.append("roles", "16")
                        Seeking.HEALER -> parameters.append("roles", "17")
                        Seeking.DPS -> parameters.append("roles", "18")
                        Seeking.CRAFTER -> parameters.append("roles", "19")
                        Seeking.GATHERER -> parameters.append("roles", "20")
                        null -> parameters.append("roles", "-1")
                    }
                }

                if (active != null) when (active) {
                    Active.WEEKDAYS_ONLY -> parameters.append("activetime", "1")
                    Active.WEEKENDS_ONLY -> parameters.append("activetime", "2")
                    Active.ALWAYS -> parameters.append("activetime", "3")
                }

                if (recruitment != null) when (recruitment) {
                    Recruitment.OPEN -> parameters.append("join", "1")
                    Recruitment.CLOSED -> parameters.append("join", "0")
                }

                if (housing != null) when (housing) {
                    Housing.ESTATE_BUILT -> parameters.append("house", "2")
                    Housing.PLOT_ONLY -> parameters.append("house", "1")
                    Housing.NO_ESTATE_OR_PLOT -> parameters.append("house", "0")
                }

                grandCompany?.forEach {
                    when (it) {
                        GrandCompanyName.MAELSTROM -> parameters.append("gcid", "1")
                        GrandCompanyName.ORDER_OF_THE_TWIN_ADDER -> parameters.append("gcid", "2")
                        GrandCompanyName.IMMORTAL_FLAMES -> parameters.append("gcid", "3")
                    }
                }

                parameters.append("page", "$page")
            }
        }.let {
            when (it.status.value) {
                200 -> Jsoup.parse(it.body() as String).let {
                    if (it.select(FreeCompanySearchSelectors.LIST_NEXT_BUTTON)
                            .attr(FreeCompanySearchSelectors.LIST_NEXT_BUTTON_ATTR) == "javascript:void(0);"
                    ) nextPage = false

                    it.select(FreeCompanySearchSelectors.ROOT).let {
                        buildList {
                            it.select(FreeCompanySearchSelectors.ENTRIES_ROOT).forEach {
                                val name = async {
                                    it.select(FreeCompanySearchSelectors.ENTRY_NAME).text()
                                }

                                val id = async {
                                    it.select(FreeCompanySearchSelectors.ENTRY_ID)
                                        .attr(FreeCompanySearchSelectors.ENTRY_ID_ATTR)
                                        .split("/")[3]
                                }

                                val crest = async {
                                    val bottom = async {
                                        it.select(FreeCompanySearchSelectors.ENTRY_BOTTOM_CREST_LAYER)
                                            .attr(FreeCompanySearchSelectors.ENTRY_CREST_LAYER_ATTR)
                                    }

                                    val middle = async {
                                        it.select(FreeCompanySearchSelectors.ENTRY_MIDDLE_CREST_LAYER)
                                            .attr(FreeCompanySearchSelectors.ENTRY_CREST_LAYER_ATTR)
                                    }

                                    val top = async {
                                        it.select(FreeCompanySearchSelectors.ENTRY_TOP_CREST_LAYER)
                                            .attr(FreeCompanySearchSelectors.ENTRY_CREST_LAYER_ATTR)
                                    }

                                    IconLayers(bottom.await(), middle.await(), top.await())
                                }

                                val world = async {
                                    World.valueOf(
                                        it.select(FreeCompanySearchSelectors.ENTRY_WORLD)
                                            .text()
                                            .split("[")[0]
                                            .trim()
                                    )
                                }

                                val dataCenter = async {
                                    DataCenter.valueOf(
                                        it.select(FreeCompanySearchSelectors.ENTRY_WORLD)
                                            .text()
                                            .split("[")[1]
                                            .replace("]", "")
                                    )
                                }

                                val activeMembers = async {
                                    it.select(FreeCompanySearchSelectors.ENTRY_ACTIVE_MEMBERS)
                                        .text()
                                        .toInt()
                                }

                                val estateBuilt = async {
                                    Housing.valueOf(
                                        it.select(FreeCompanySearchSelectors.ENTRY_ESTATE_BUILT)
                                            .text()
                                            .replace(" ", "_")
                                            .uppercase()
                                    )
                                }

                                val formed = async {
                                    FreeCompanySearchSelectors.ENTRY_FORMED_REGEX.find(
                                        it.select(FreeCompanySearchSelectors.ENTRY_FORMED).html()
                                    )!!.value.toLong()
                                }

                                val active = async {
                                    val active = it.select(FreeCompanySearchSelectors.ENTRY_ACTIVE)
                                        .text()

                                    if (active != "Active: Not specified") Active.valueOf(
                                        active.split("Active: ")[1]
                                            .replace(" ", "_")
                                            .uppercase()
                                    ) else null
                                }

                                val recruitment = async {
                                    Recruitment.valueOf(
                                        it.select(FreeCompanySearchSelectors.ENTRY_RECRUITMENT_OPEN)
                                            .text()
                                            .split("Recruitment: ")[1]
                                            .replace(" ", "_")
                                            .uppercase()
                                    )
                                }

                                val focus = async {
                                    buildList {
                                        it.select(FreeCompanySearchSelectors.ENTRY_FOCUSES)
                                            .not(FreeCompanySearchSelectors.ENTRY_FOCUS_OFF)
                                            .forEach {
                                                add(
                                                    FreeCompanySearchMaps.FOCUS_MAP.getValue(
                                                        it.select(FreeCompanySearchSelectors.ENTRY_FOCUS)
                                                            .attr(FreeCompanySearchSelectors.ENTRY_FOCUS_ATTR)
                                                    )
                                                )
                                            }
                                    }
                                }

                                val seeking = async {
                                    buildList {
                                        it.select(FreeCompanySearchSelectors.ENTRY_SEEKING_LIST)
                                            .not(FreeCompanySearchSelectors.ENTRY_SEEKING_OFF)
                                            .forEach {
                                                add(
                                                    FreeCompanySearchMaps.SEEKING_MAP.getValue(
                                                        it.select(FreeCompanySearchSelectors.ENTRY_SEEKING)
                                                            .attr(FreeCompanySearchSelectors.ENTRY_SEEKING_ATTR)
                                                    )
                                                )
                                            }
                                    }
                                }

                                add(
                                    FreeCompanySearchResult(
                                        name.await(),
                                        id.await(),
                                        crest.await(),
                                        world.await(),
                                        dataCenter.await(),
                                        activeMembers.await(),
                                        estateBuilt.await(),
                                        formed.await(),
                                        active.await(),
                                        recruitment.await(),
                                        focus.await(),
                                        seeking.await()
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
