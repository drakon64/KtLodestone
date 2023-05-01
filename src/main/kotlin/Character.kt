package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.ActiveClassJob
import cloud.drakon.ktlodestone.profile.GrandCompany
import cloud.drakon.ktlodestone.profile.Guardian
import cloud.drakon.ktlodestone.profile.ProfileCharacter
import cloud.drakon.ktlodestone.profile.Town
import cloud.drakon.ktlodestone.profile.guild.Guild
import cloud.drakon.ktlodestone.profile.guild.GuildType
import cloud.drakon.ktlodestone.profile.guild.IconLayers
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object Character {
    private val lodestoneCssSelectors = Json.parseToJsonElement(
        this::class.java.classLoader.getResource("lodestone-css-selectors/profile/character.json") !!
            .readText()
    )

    private val raceRegex = """^[^<]*""".toRegex()
    private val clanRegex = """(?<=<br>\n).*?(?= /)""".toRegex()
    private val genderRegex = """(?<=/ ).*""".toRegex()
    private val serverRegex = """\S+""".toRegex()
    private val dcRegex = """(?<=\[)\w+(?=\])""".toRegex()

    /**
     * Gets a character from The Lodestone
     * @param id The Lodestone character ID
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    suspend fun getCharacter(id: Int) = coroutineScope {
        val request =
            ktorClient.get("https://eu.finalfantasyxiv.com/lodestone/character/${id}/")
        val character = when (request.status.value) {
            200 -> Jsoup.parse(request.body() as String)
            404 -> throw CharacterNotFoundException("Thrown when a character could not be found on The Lodestone.")
            else -> throw LodestoneException("Thrown when The Lodestone returns an unknown error.")
        }

        val activeClassJob = async { getActiveClassJob(character) }

        val avatar = async {
            character.select(getLodestoneCss("AVATAR"))
                .first() !!
                .attr(getLodestoneCss("AVATAR", "attribute"))
        }

        val bio = async {
            character.select(getLodestoneCss("BIO")).first() !!.text()
        }

        val freeCompany = async { getGuild(character, GuildType.FREE_COMPANY) }
        val grandCompany = async { getGrandCompany(character) }
        val guardian = async { getGuardian(character) }

        val name = async {
            character.select(getLodestoneCss("NAME")).first() !!.text()
        }

        val nameday = async {
            character.select(getLodestoneCss("NAMEDAY")).first() !!.text()
        }

        val portrait = async {
            character.select(getLodestoneCss("PORTRAIT"))
                .first() !!
                .attr(getLodestoneCss("PORTRAIT", "attribute"))
        }

        val pvpTeam = async { getGuild(character, GuildType.PVP_TEAM) }

        val raceClanGender = async {
            character.select(getLodestoneCss("RACE_CLAN_GENDER")).first() !!.html()
        }
        val race = async { raceRegex.find(raceClanGender.await()) !!.value }
        val clan = async { clanRegex.find(raceClanGender.await()) !!.value }
        val gender = async { genderRegex.find(raceClanGender.await()) !!.value }

        val serverDc = async {
            character.select(getLodestoneCss("SERVER")).first() !!.text()
        }
        val server = async { serverRegex.find(serverDc.await()) !!.value }
        val dc = async { dcRegex.find(serverDc.await()) !!.value }

        val title = async { character.select(getLodestoneCss("TITLE")).first()?.text() }

        val town = async { getTown(character) }

        ProfileCharacter(
            activeClassJob.await(),
            avatar.await(),
            bio.await(),
            freeCompany.await(),
            grandCompany.await(),
            guardian.await(),
            name.await(),
            nameday.await(),
            portrait.await(),
            pvpTeam.await(),
            race.await(),
            clan.await(),
            gender.await(),
            server.await(),
            dc.await(),
            title.await(),
            town.await(),
        )
    }

    private suspend fun getLodestoneCss(
        lodestoneProperty: String,
        cssProperty: String = "selector",
    ) = coroutineScope {
        lodestoneCssSelectors.jsonObject[lodestoneProperty] !!.jsonObject[cssProperty] !!.jsonPrimitive.content
    }

    private val activeClassJobLevelRegex = """\d+""".toRegex()

    private suspend fun getActiveClassJob(character: Document) = coroutineScope {
        val name = async {
            val classJobUrl =
                character.select(getLodestoneCss("ACTIVE_CLASSJOB"))
                    .first() !!
                    .attr(getLodestoneCss("ACTIVE_CLASSJOB", "attribute"))

            val prefix = "https://img.finalfantasyxiv.com/lds/h"
            val classJob = mapOf(
                "${prefix}/E/d0Tx-vhnsMYfYpGe9MvslemEfg.png" to "Paladin",
                "${prefix}/U/F5JzG9RPIKFSogtaKNBk455aYA.png" to "Gladiator",
                "${prefix}/y/A3UhbjZvDeN3tf_6nJ85VP0RY0.png" to "Warrior",
                "${prefix}/N/St9rjDJB3xNKGYg-vwooZ4j6CM.png" to "Marauder",
                "${prefix}/l/5CZEvDOMYMyVn2td9LZigsgw9s.png" to "Dark Knight",
                "${prefix}/8/hg8ofSSOKzqng290No55trV4mI.png" to "Gunbreaker",

                "${prefix}/7/i20QvSPcSQTybykLZDbQCgPwMw.png" to "White Mage",
                "${prefix}/s/gl62VOTBJrm7D_BmAZITngUEM8.png" to "Conjurer",
                "${prefix}/7/WdFey0jyHn9Nnt1Qnm-J3yTg5s.png" to "Scholar",
                "${prefix}/1/erCgjnMSiab4LiHpWxVc-tXAqk.png" to "Astrologian",
                "${prefix}/g/_oYApASVVReLLmsokuCJGkEpk0.png" to "Sage",

                "${prefix}/K/HW6tKOg4SOJbL8Z20GnsAWNjjM.png" to "Monk",
                "${prefix}/V/iW7IBKQ7oglB9jmbn6LwdZXkWw.png" to "Pugilist",
                "${prefix}/m/gX4OgBIHw68UcMU79P7LYCpldA.png" to "Dragoon",
                "${prefix}/k/tYTpoSwFLuGYGDJMff8GEFuDQs.png" to "Lancer",
                "${prefix}/0/Fso5hanZVEEAaZ7OGWJsXpf3jw.png" to "Ninja",
                "${prefix}/y/wdwVVcptybfgSruoh8R344y_GA.png" to "Rogue",
                "${prefix}/m/KndG72XtCFwaq1I1iqwcmO_0zc.png" to "Samurai",
                "${prefix}/7/cLlXUaeMPJDM2nBhIeM-uDmPzM.png" to "Reaper",

                "${prefix}/F/KWI-9P3RX_Ojjn_mwCS2N0-3TI.png" to "Bard",
                "${prefix}/Q/ZpqEJWYHj9SvHGuV9cIyRNnIkk.png" to "Archer",
                "${prefix}/E/vmtbIlf6Uv8rVp2YFCWA25X0dc.png" to "Machinist",
                "${prefix}/t/HK0jQ1y7YV9qm30cxGOVev6Cck.png" to "Dancer",

                "${prefix}/P/V01m8YRBYcIs5vgbRtpDiqltSE.png" to "Black Mage",
                "${prefix}/t/HK0jQ1y7YV9qm30cxGOVev6Cck.png" to "Thaumaturge",
                "${prefix}/h/4ghjpyyuNelzw1Bl0sM_PBA_FE.png" to "Summoner",
                "${prefix}/e/VYP1LKTDpt8uJVvUT7OKrXNL9E.png" to "Arcanist",
                "${prefix}/q/s3MlLUKmRAHy0pH57PnFStHmIw.png" to "Red Mage",
                "${prefix}/p/jdV3RRKtWzgo226CC09vjen5sk.png" to "Blue Mage",

                "${prefix}/v/YCN6F-xiXf03Ts3pXoBihh2OBk.png" to "Carpenter",
                "${prefix}/5/EEHVV5cIPkOZ6v5ALaoN5XSVRU.png" to "Blacksmith",
                "${prefix}/G/Rq5wcK3IPEaAB8N-T9l6tBPxCY.png" to "Armorer",
                "${prefix}/L/LbEjgw0cwO_2gQSmhta9z03pjM.png" to "Goldsmith",
                "${prefix}/b/ACAcQe3hWFxbWRVPqxKj_MzDiY.png" to "Leatherworker",
                "${prefix}/X/E69jrsOMGFvFpCX87F5wqgT_Vo.png" to "Weaver",
                "${prefix}/C/bBVQ9IFeXqjEdpuIxmKvSkqalE.png" to "Alchemist",
                "${prefix}/m/1kMI2v_KEVgo30RFvdFCyySkFo.png" to "Culinarian",

                "${prefix}/A/aM2Dd6Vo4HW_UGasK7tLuZ6fu4.png" to "Miner",
                "${prefix}/I/jGRnjIlwWridqM-mIPNew6bhHM.png" to "Botanist",
                "${prefix}/I/jGRnjIlwWridqM-mIPNew6bhHM.png" to "Fisher"
            )

            classJob.getValue(classJobUrl)
        }

        val level = async {
            activeClassJobLevelRegex.find(
                character.select(getLodestoneCss("ACTIVE_CLASSJOB_LEVEL"))
                    .first() !!
                    .text()
            ) !!.value.toByte()
        }

        return@coroutineScope ActiveClassJob(name.await(), level.await())
    }

    private val freeCompanyIdRegex = """\d+""".toRegex()
    private val pvpTeamIdRegex = """/lodestone/pvpteam/(.*?)/""".toRegex()

    private suspend fun getGuild(character: Document, type: GuildType) =
        coroutineScope {
            val selectorJson = lodestoneCssSelectors.jsonObject[type.name] !!
            val nameSelectorJson = selectorJson.jsonObject["NAME"] !!

            val guild = async {
                character.select(nameSelectorJson.jsonObject["selector"] !!.jsonPrimitive.content)
                    .first()
            }

            if (guild.await() != null) {
                val guildName = async { guild.await() !!.text() }
                val guildId = async {
                    val regex = when (type) {
                        GuildType.FREE_COMPANY -> {
                            freeCompanyIdRegex
                        }

                        GuildType.PVP_TEAM -> {
                            pvpTeamIdRegex
                        }
                    }

                    regex.find(
                        guild.await() !!
                            .attr(nameSelectorJson.jsonObject["attribute"] !!.jsonPrimitive.content)
                    ) !!.value
                }

                val guildIconLayerBottom = async {
                    getIconLayer("BOTTOM", character, selectorJson)
                }
                val guildIconLayerMiddle = async {
                    getIconLayer("MIDDLE", character, selectorJson)
                }
                val guildIconLayerTop = async {
                    getIconLayer("TOP", character, selectorJson)
                }

                Guild(
                    name = guildName.await(),
                    id = guildId.await(),
                    iconLayers = IconLayers(
                        bottom = guildIconLayerBottom.await(),
                        middle = guildIconLayerMiddle.await(),
                        top = guildIconLayerTop.await()
                    )
                )
            } else {
                null
            }
        }

    private suspend fun getIconLayer(
        iconLayer: String,
        character: Document,
        jsonElement: JsonElement,
    ) = coroutineScope {
        val selectorJson =
            jsonElement.jsonObject["ICON_LAYERS"] !!.jsonObject[iconLayer] !!

        character.select(selectorJson.jsonObject["selector"] !!.jsonPrimitive.content)
            .first() !!
            .attr(selectorJson.jsonObject["attribute"] !!.jsonPrimitive.content)
    }

    private val grandCompanyNameRegex = """\w+""".toRegex()
    private val grandCompanyRankRegex = """(?<=\/ ).*""".toRegex()

    private suspend fun getGrandCompany(character: Document) = coroutineScope {
        val selectorJson = lodestoneCssSelectors.jsonObject["GRAND_COMPANY"] !!

        val grandCompany = async {
            character.select(selectorJson.jsonObject["selector"] !!.jsonPrimitive.content)
                .first()
                ?.text()
        }

        if (grandCompany.await() != null) {
            val grandCompanyName = async {
                grandCompanyNameRegex.find(
                    grandCompany.await() !!
                ) !!.value
            }
            val grandCompanyRank = async {
                grandCompanyRankRegex.find(
                    grandCompany.await() !!
                ) !!.value
            }
            val grandCompanyIcon = async {
                character.select("#character > div.character__content.selected > div.character__profile.clearfix > div.character__profile__data > div:nth-child(1) > div > div:nth-child(4) > img")
                    .first() !!
                    .attr("src")
            }

            GrandCompany(
                name = grandCompanyName.await(),
                rank = grandCompanyRank.await(),
                icon = grandCompanyIcon.await()
            )
        } else {
            null
        }
    }

    private suspend fun getGuardian(character: Document) = coroutineScope {
        val selectorJson = lodestoneCssSelectors.jsonObject["GUARDIAN_DEITY"] !!

        val guardianName = async {
            character.select(selectorJson.jsonObject["NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .text()
        }

        val iconSelectorJson = selectorJson.jsonObject["ICON"] !!
        val guardianIcon = async {
            character.select(iconSelectorJson.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .attr(iconSelectorJson.jsonObject["attribute"] !!.jsonPrimitive.content)
        }

        Guardian(name = guardianName.await(), icon = guardianIcon.await())
    }

    private suspend fun getTown(character: Document) = coroutineScope {
        val selectorJson = lodestoneCssSelectors.jsonObject["TOWN"] !!

        val townName = async {
            character.select(selectorJson.jsonObject["NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .text()
        }

        val iconSelectorJson = selectorJson.jsonObject["ICON"] !!
        val townIcon = async {
            character.select(iconSelectorJson.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .attr(iconSelectorJson.jsonObject["attribute"] !!.jsonPrimitive.content)
        }

        Town(name = townName.await(), icon = townIcon.await())
    }
}
