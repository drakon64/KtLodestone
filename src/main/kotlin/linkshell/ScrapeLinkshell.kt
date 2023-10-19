package cloud.drakon.ktlodestone.linkshell

import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyRank
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import cloud.drakon.ktlodestone.ktorClient
import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileMaps
import cloud.drakon.ktlodestone.selectors.linkshell.LinkshellMemberSelectors
import cloud.drakon.ktlodestone.selectors.linkshell.LinkshellSelectors
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

internal class ScrapeLinkshell(private val id: String) {
    private val linkshellMembers = mutableListOf<LinkshellMember>()

    suspend fun scrapeLinkshell(response: String) = coroutineScope {
        Jsoup.parse(response).let {
            val name = async {
                it.select(LinkshellSelectors.NAME).text()
            }

            val dataCenter = async {
                it.select(LinkshellSelectors.DATACENTER).first()?.text()?.let {
                    DataCenter.valueOf(it)
                }
            }

            val region = async {
                dataCenter.await()?.let {
                    CharacterProfileMaps.REGION_MAP.getValue(it)
                }
            }

            val formed = async {
                it.select(LinkshellSelectors.FORMED).first()?.html()?.let {
                    LinkshellSelectors.FORMED_REGEX.find(it)!!.value.toLong()
                }
            }

            val activeMembers = async {
                LinkshellSelectors.ACTIVE_MEMBERS_REGEX.find(
                    it.select(LinkshellSelectors.ACTIVE_MEMBERS).text()
                )!!.value.toShort()
            }

            val members = async {
                getLinkshellMembers(it)
                linkshellMembers
            }

            Linkshell(
                name.await(),
                formed.await(),
                dataCenter.await(),
                region.await(),
                activeMembers.await(),
                members.await().toList(),
            )
        }
    }

    private suspend fun getLinkshellMembers(response: Document): Unit = coroutineScope {
        response.select(LinkshellMemberSelectors.ROOT).let {
            it.select(LinkshellMemberSelectors.ENTRY_ROOT).forEach {
                val avatar = async {
                    it.select(LinkshellMemberSelectors.ENTRY_AVATAR)
                        .attr(LinkshellMemberSelectors.ENTRY_AVATAR_ATTR)
                }

                val id = async {
                    LinkshellMemberSelectors.ENTRY_ID_REGEX.find(
                        it.select(LinkshellMemberSelectors.ENTRY_ID)
                            .attr(LinkshellMemberSelectors.ENTRY_ID_ATTR)
                    )!!.value
                        .toInt()
                }

                val name = async {
                    it.select(LinkshellMemberSelectors.ENTRY_NAME).text()
                }

                val grandCompany = async {
                    it.select(LinkshellMemberSelectors.ENTRY_RANK)
                        .first()
                        ?.attr(LinkshellMemberSelectors.ENTRY_RANK_ATTR)
                        ?.uppercase()
                        ?.split("/")
                        ?.let {
                            val name = async {
                                GrandCompanyName.valueOf(
                                    it[0].trim().replace(" ", "_")
                                )
                            }

                            val rank = async {
                                GrandCompanyRank.valueOf(
                                    it[1].trim().replace(" ", "_")
                                )
                            }

                            GrandCompany(name.await(), rank.await())
                        }
                }

                val linkshellRank = async {
                    it.select(LinkshellMemberSelectors.ENTRY_LINKSHELL_RANK)
                        .first()
                        ?.text()
                }

                val worldDataCenter = async {
                    it.select(LinkshellMemberSelectors.ENTRY_WORLD)
                        .text()
                        .split("[")
                }

                val world = async {
                    World.valueOf(worldDataCenter.await()[0].trim())
                }

                val dataCenter = async {
                    DataCenter.valueOf(
                        worldDataCenter.await()[1].trim().replace("]", "")
                    )
                }

                val region = async {
                    CharacterProfileMaps.REGION_MAP.getValue(dataCenter.await())
                }

                linkshellMembers.add(
                    LinkshellMember(
                        name.await(),
                        id.await(),
                        avatar.await(),
                        grandCompany.await(),
                        linkshellRank.await(),
                        world.await(),
                        dataCenter.await(),
                        region.await()
                    )
                )
            }
        }

        response.select(LinkshellMemberSelectors.LIST_NEXT_BUTTON)
            .attr(LinkshellMemberSelectors.LIST_NEXT_BUTTON_ATTR).let {
                if (it != "javascript:void(0);") ktorClient.get(it).let {
                    when (it.status.value) {
                        200 -> getLinkshellMembers(Jsoup.parse(it.body<String>()))
                        404 -> throw LodestoneNotFoundException(id, "Linkshell")
                        else -> throw LodestoneException()
                    }
                }
            }
    }
}
