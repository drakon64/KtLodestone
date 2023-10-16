package cloud.drakon.ktlodestone.linkshell.search

import cloud.drakon.ktlodestone.exception.InvalidParameterException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.ktorClient
import cloud.drakon.ktlodestone.search.ActiveMembers
import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileMaps
import cloud.drakon.ktlodestone.selectors.linkshell.search.LinkshellSearchSelectors
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

// To ensure that `nextPage` isn't accessed statically, these functions get their own class
internal class LinkshellSearch {
    private var nextPage = true

    suspend fun scrapeLinkshellSearch(
        name: String?,
        communityFinder: Boolean,
        world: World?,
        dataCenter: DataCenter?,
        activeMembers: ActiveMembers?,
        crossWorld: Boolean,
        pages: Byte,
    ) = coroutineScope {
        var page: Byte = 1

        buildList {
            while (page <= pages && nextPage) {
                add(
                    searchLodestoneLinkshellSearchPaginated(
                        name,
                        communityFinder,
                        world,
                        dataCenter,
                        activeMembers,
                        crossWorld,
                        page
                    )
                )

                page++
            }
        }.flatten()
    }

    private suspend fun searchLodestoneLinkshellSearchPaginated(
        name: String?,
        communityFinder: Boolean,
        world: World?,
        dataCenter: DataCenter?,
        activeMembers: ActiveMembers?,
        crossWorld: Boolean,
        page: Byte,
    ): List<LinkshellSearchResult> = coroutineScope {
        ktorClient.get(if (crossWorld) "crossworld_linkshell/" else "linkshell/") {
            url {
                // We've already encoded this parameter
                if (name != null) encodedParameters.append("q", name.replace(" ", "+"))

                if (communityFinder) parameters.append("cf_public", "1")

                // If [world] is provided, use it as it's more specific, otherwise use [dataCenter]
                if (world != null) {
                    if (crossWorld) throw InvalidParameterException("Parameter `world` must be `null` when `crossWorld` is `true`.")

                    parameters.append("worldname", world.name)
                } else if (dataCenter != null) {
                    parameters.append("worldname", "_dc_${dataCenter.name}")
                }

                if (activeMembers != null) parameters.append("character_count", "$activeMembers")

                parameters.append("page", "$page")
            }
        }.let {
            when (it.status.value) {
                200 -> Jsoup.parse(it.body() as String).let {
                    if (it.select(LinkshellSearchSelectors.LIST_NEXT_BUTTON)
                            .attr(LinkshellSearchSelectors.LIST_NEXT_BUTTON_ATTR) == "javascript:void(0);"
                    ) nextPage = false

                    it.select(LinkshellSearchSelectors.ROOT).let {
                        buildList {
                            it.select(LinkshellSearchSelectors.ENTRIES_ROOT).forEach {
                                val name = async {
                                    it.select(LinkshellSearchSelectors.ENTRY_NAME).text()
                                }

                                val id = async {
                                    it.select(LinkshellSearchSelectors.ENTRY_ID)
                                        .attr(LinkshellSearchSelectors.ENTRY_ID_ATTR)
                                        .split("/")[3]
                                }

                                val world: Deferred<World?>
                                val dataCenter: Deferred<DataCenter>

                                if (crossWorld) {
                                    world = async {
                                        null
                                    }

                                    dataCenter = async {
                                        DataCenter.valueOf(
                                            it.select(LinkshellSearchSelectors.ENTRY_WORLD).text()
                                        )
                                    }
                                } else {
                                    async {
                                        it.select(LinkshellSearchSelectors.ENTRY_WORLD)
                                            .text()
                                            .split("[")
                                    }.let {
                                        world = async {
                                            World.valueOf(it.await()[0].trim())
                                        }

                                        dataCenter = async {
                                            DataCenter.valueOf(
                                                it.await()[1].replace("]", "").trim()
                                            )
                                        }
                                    }
                                }

                                val region = async {
                                    CharacterProfileMaps.REGION_MAP.getValue(dataCenter.await())
                                }

                                val activeMembers = async {
                                    it.select(LinkshellSearchSelectors.ENTRY_ACTIVE_MEMBERS)
                                        .text()
                                        .toInt()
                                }

                                add(
                                    LinkshellSearchResult(
                                        name.await(),
                                        id.await(),
                                        world.await(),
                                        dataCenter.await(),
                                        region.await(),
                                        activeMembers.await(),
                                        crossWorld,
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
