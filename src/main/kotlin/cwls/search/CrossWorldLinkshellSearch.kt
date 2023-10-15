package cloud.drakon.ktlodestone.cwls.search

import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.ktorClient
import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileMaps
import cloud.drakon.ktlodestone.selectors.cwls.search.CrossWorldLinkshellSearchSelectors
import cloud.drakon.ktlodestone.world.DataCenter
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

// To ensure that `nextPage` isn't accessed statically, these functions get their own class
internal class CrossWorldLinkshellSearch {
    private var nextPage = true

    suspend fun scrapeCrossWorldLinkshellSearch(
        name: String?,
        communityFinder: Boolean,
        dataCenter: DataCenter?,
        characterCount: CharacterCount?,
        pages: Byte,
    ) = coroutineScope {
        var page: Byte = 1

        buildList {
            while (page <= pages && nextPage) {
                add(
                    searchLodestoneCrossWorldLinkshellSearchPaginated(
                        name,
                        communityFinder,
                        dataCenter,
                        characterCount,
                        page
                    )
                )

                page++
            }
        }.flatten()
    }

    private suspend fun searchLodestoneCrossWorldLinkshellSearchPaginated(
        name: String?,
        communityFinder: Boolean,
        dataCenter: DataCenter?,
        characterCount: CharacterCount?,
        page: Byte,
    ): List<CrossWorldLinkshellSearchResult> = coroutineScope {
        ktorClient.get("crossworld_linkshell/") {
            url {
                // We've already encoded this parameter
                if (name != null) encodedParameters.append("q", name.replace(" ", "+"))

                if (communityFinder) parameters.append("cf_public", "1")

                if (dataCenter != null) parameters.append("dcname", "$dataCenter")

                if (characterCount != null) parameters.append("character_count", "$characterCount")

                parameters.append("page", "$page")
            }
        }.let {
            when (it.status.value) {
                200 -> Jsoup.parse(it.body() as String).let {
                    if (it.select(CrossWorldLinkshellSearchSelectors.LIST_NEXT_BUTTON)
                            .attr(CrossWorldLinkshellSearchSelectors.LIST_NEXT_BUTTON_ATTR) == "javascript:void(0);"
                    ) nextPage = false

                    it.select(CrossWorldLinkshellSearchSelectors.ROOT).let {
                        buildList {
                            it.select(CrossWorldLinkshellSearchSelectors.ENTRIES_ROOT).forEach {
                                val name = async {
                                    it.select(CrossWorldLinkshellSearchSelectors.ENTRY_NAME).text()
                                }

                                val id = async {
                                    it.select(CrossWorldLinkshellSearchSelectors.ENTRY_ID)
                                        .attr(CrossWorldLinkshellSearchSelectors.ENTRY_ID_ATTR)
                                        .split("/")[3]
                                }

                                val dataCenter = async {
                                    DataCenter.valueOf(
                                        it.select(CrossWorldLinkshellSearchSelectors.ENTRY_DATACENTER)
                                            .text()
                                    )
                                }

                                val region = async {
                                    CharacterProfileMaps.REGION_MAP.getValue(dataCenter.await())
                                }

                                val activeMembers = async {
                                    it.select(CrossWorldLinkshellSearchSelectors.ENTRY_ACTIVE_MEMBERS)
                                        .text()
                                        .toInt()
                                }

                                add(
                                    CrossWorldLinkshellSearchResult(
                                        name.await(),
                                        id.await(),
                                        dataCenter.await(),
                                        region.await(),
                                        activeMembers.await(),
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
