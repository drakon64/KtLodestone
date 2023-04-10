package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.Character
import cloud.drakon.ktlodestone.profile.freecompany.FreeCompany
import cloud.drakon.ktlodestone.profile.freecompany.FreeCompanyIconLayers
import cloud.drakon.ktlodestone.profile.guardiandeity.GuardianDeity
import cloud.drakon.ktlodestone.profile.pvpteam.PvpTeam
import cloud.drakon.ktlodestone.profile.pvpteam.PvpTeamIconLayers
import cloud.drakon.ktlodestone.profile.town.Town
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

object Character {
    private val activeClassJobLevelRegex = """\d+""".toRegex()
    private val grandCompanyRegex = """\w+""".toRegex()
    private val grandCompanyRankRegex = """(?<=\/ ).*""".toRegex()
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
    suspend fun getCharacter(id: Int): Character {
        return coroutineScope {
            val request =
                ktorClient.get("https://eu.finalfantasyxiv.com/lodestone/character/${id}/")
            val character = when (request.status.value) {
                200 -> Jsoup.parse(request.body() as String)
                404 -> throw CharacterNotFoundException("Thrown when a character could not be found on The Lodestone.")
                else -> throw LodestoneException("Thrown when The Lodestone returns an unknown error.")
            }

            val activeClassJob = async {
                character.select(".character__class_icon > img:nth-child(1)")
                    .first() !!
                    .attr("src")
            }

            val activeClassJobLevel = async {
                activeClassJobLevelRegex.find(
                    character.select(".character__class__data > p:nth-child(1)")
                        .first() !!
                        .text()
                ) !!.value.toByte()
            }

            val avatar = async {
                character.select(".frame__chara__face > img:nth-child(1)")
                    .first() !!
                    .attr("src")
            }

            val bio = async {
                character.select(".character__selfintroduction").first() !!.text()
            }

            val freeCompanyName = async {
                character.select(".character__freecompany__name > h4:nth-child(2) > a:nth-child(1)")
                    .first()
                    ?.text()
            }
            val freeCompanyId = async {
                character.select(".character__freecompany__name > h4:nth-child(2) > a:nth-child(1)")
                    .first()
                    ?.attr("href")
            }
            val freeCompanyIconLayers = async {
                if (freeCompanyId.await() != null) {
                    FreeCompanyIconLayers(
                        bottom = character.select("div.character__freecompany__crest > div > img:nth-child(1)")
                            .first()
                            ?.attr("src")
                            .toString(),
                        middle = character.select("div.character__freecompany__crest > div > img:nth-child(2)")
                            .first()
                            ?.attr("src")
                            .toString(),
                        top = character.select("div.character__freecompany__crest > div > img:nth-child(3)")
                            .first()
                            ?.attr("src")
                            .toString()
                    )
                } else {
                    null
                }
            }
            val freeCompany = async {
                if (freeCompanyId.await() != null && freeCompanyIconLayers.await() != null) {
                    FreeCompany(
                        name = freeCompanyName.await() !!,
                        id = freeCompanyId.await() !!,
                        iconLayers = freeCompanyIconLayers.await() !!
                    )
                } else {
                    null
                }
            }

            val grandCompanySelect = async {
                character.select("div.character-block:nth-child(4) > div:nth-child(2) > p:nth-child(2)")
                    .first()
                    ?.text()
            }
            val grandCompany = async {
                if (grandCompanySelect.await() != null) {
                    grandCompanyRegex.find(
                        grandCompanySelect.await() !!
                    ) !!.value

                } else {
                    null
                }
            }
            val grandCompanyRank = async {
                if (grandCompanySelect.await() != null) {
                    grandCompanyRankRegex.find(
                        grandCompanySelect.await() !!
                    ) !!.value

                } else {
                    null
                }
            }

            val guardianDeityName = async {
                character.select("p.character-block__name:nth-child(4)")
                    .first() !!
                    .text()
            }
            val guardianDeityIcon = async {
                character.select("#character > div.character__content.selected > div.character__profile.clearfix > div.character__profile__data > div:nth-child(1) > div > div:nth-child(2) > img")
                    .first() !!
                    .attr("src")
            }
            val guardianDeity = async {
                GuardianDeity(
                    name = guardianDeityName.await(), icon = guardianDeityIcon.await()
                )
            }

            val name = async {
                character.select("div.frame__chara__box:nth-child(2) > .frame__chara__name")
                    .first() !!
                    .text()
            }

            val nameday = async {
                character.select(".character-block__birth").first() !!.text()
            }

            val portrait = async {
                character.select(".js__image_popup > img:nth-child(1)")
                    .first() !!
                    .attr("src")
            }

            val pvpTeamName = async {
                character.select(".character__pvpteam__name > h4:nth-child(2) > a:nth-child(1)")
                    .first()
                    ?.attr("href")
            }
            val pvpTeamIconLayersBottom = async {
                if (pvpTeamName.await() != null) {
                    character.select(".character__pvpteam__crest__image img:nth-child(1)")
                        .first()
                        ?.attr("src")
                } else {
                    null
                }
            }
            val pvpTeamIconLayersMiddle = async {
                if (pvpTeamName.await() != null) {
                    character.select(".character__pvpteam__crest__image img:nth-child(2)")
                        .first()
                        ?.attr("src")
                } else {
                    null
                }
            }
            val pvpTeamIconLayersTop = async {
                if (pvpTeamName.await() != null) {
                    character.select(".character__pvpteam__crest__image img:nth-child(3)")
                        .first()
                        ?.attr("src")
                } else {
                    null
                }
            }
            val pvpTeamIconLayers = async {
                if (pvpTeamName.await() != null) {
                    PvpTeamIconLayers(
                        bottom = pvpTeamIconLayersBottom.await() !!,
                        middle = pvpTeamIconLayersMiddle.await() !!,
                        top = pvpTeamIconLayersTop.await() !!
                    )
                } else {
                    null
                }
            }
            val pvpTeam = async {
                if (pvpTeamName.await() != null && pvpTeamIconLayers.await() != null) {
                    PvpTeam(
                        name = pvpTeamName.await() !!,
                        iconLayers = pvpTeamIconLayers.await() !!
                    )
                } else {
                    null
                }
            }

            val raceClanGender = async {
                character.select("div.character-block:nth-child(1) > div:nth-child(2) > p:nth-child(2)")
                    .first() !!
                    .html()
            }
            val race = async {
                raceRegex.find(raceClanGender.await()) !!.value
            }
            val clan = async {
                clanRegex.find(raceClanGender.await()) !!.value
            }
            val gender = async {
                genderRegex.find(raceClanGender.await()) !!.value
            }

            val serverDc = async {
                character.select("p.frame__chara__world").first() !!.text()
            }
            val server = async { serverRegex.find(serverDc.await()) !!.value }
            val dc = async { dcRegex.find(serverDc.await()) !!.value }

            val title = async {
                character.select(".frame__chara__title").first()?.text().toString()
            }

            val townName = async {
                character.select("div.character-block:nth-child(3) > div:nth-child(2) > p:nth-child(2)")
                    .first() !!
                    .text()
            }
            val townIcon = async {
                character.select("#character > div.character__content.selected > div.character__profile.clearfix > div.character__profile__data > div:nth-child(1) > div > div:nth-child(3) > img")
                    .first() !!
                    .attr("src")
            }
            val town = async {
                Town(
                    name = townName.await(), icon = townIcon.await()
                )
            }

            val classJob = mapOf(
                "https://img.finalfantasyxiv.com/lds/h/E/d0Tx-vhnsMYfYpGe9MvslemEfg.png" to "Paladin",
                "https://img.finalfantasyxiv.com/lds/h/U/F5JzG9RPIKFSogtaKNBk455aYA.png" to "Gladiator",
                "https://img.finalfantasyxiv.com/lds/h/y/A3UhbjZvDeN3tf_6nJ85VP0RY0.png" to "Warrior",
                "https://img.finalfantasyxiv.com/lds/h/N/St9rjDJB3xNKGYg-vwooZ4j6CM.png" to "Marauder",
                "https://img.finalfantasyxiv.com/lds/h/l/5CZEvDOMYMyVn2td9LZigsgw9s.png" to "Dark Knight",
                "https://img.finalfantasyxiv.com/lds/h/8/hg8ofSSOKzqng290No55trV4mI.png" to "Gunbreaker",

                "https://img.finalfantasyxiv.com/lds/h/7/i20QvSPcSQTybykLZDbQCgPwMw.png" to "White Mage",
                "https://img.finalfantasyxiv.com/lds/h/s/gl62VOTBJrm7D_BmAZITngUEM8.png" to "Conjurer",
                "https://img.finalfantasyxiv.com/lds/h/7/WdFey0jyHn9Nnt1Qnm-J3yTg5s.png" to "Scholar",
                "https://img.finalfantasyxiv.com/lds/h/1/erCgjnMSiab4LiHpWxVc-tXAqk.png" to "Astrologian",
                "https://img.finalfantasyxiv.com/lds/h/g/_oYApASVVReLLmsokuCJGkEpk0.png" to "Sage",

                "https://img.finalfantasyxiv.com/lds/h/K/HW6tKOg4SOJbL8Z20GnsAWNjjM.png" to "Monk",
                "https://img.finalfantasyxiv.com/lds/h/V/iW7IBKQ7oglB9jmbn6LwdZXkWw.png" to "Pugilist",
                "https://img.finalfantasyxiv.com/lds/h/m/gX4OgBIHw68UcMU79P7LYCpldA.png" to "Dragoon",
                "https://img.finalfantasyxiv.com/lds/h/k/tYTpoSwFLuGYGDJMff8GEFuDQs.png" to "Lancer",
                "https://img.finalfantasyxiv.com/lds/h/0/Fso5hanZVEEAaZ7OGWJsXpf3jw.png" to "Ninja",
                "https://img.finalfantasyxiv.com/lds/h/y/wdwVVcptybfgSruoh8R344y_GA.png" to "Rogue",
                "https://img.finalfantasyxiv.com/lds/h/m/KndG72XtCFwaq1I1iqwcmO_0zc.png" to "Samurai",
                "https://img.finalfantasyxiv.com/lds/h/7/cLlXUaeMPJDM2nBhIeM-uDmPzM.png" to "Reaper",

                "https://img.finalfantasyxiv.com/lds/h/F/KWI-9P3RX_Ojjn_mwCS2N0-3TI.png" to "Bard",
                "https://img.finalfantasyxiv.com/lds/h/Q/ZpqEJWYHj9SvHGuV9cIyRNnIkk.png" to "Archer",
                "https://img.finalfantasyxiv.com/lds/h/E/vmtbIlf6Uv8rVp2YFCWA25X0dc.png" to "Machinist",
                "https://img.finalfantasyxiv.com/lds/h/t/HK0jQ1y7YV9qm30cxGOVev6Cck.png" to "Dancer",

                "https://img.finalfantasyxiv.com/lds/h/P/V01m8YRBYcIs5vgbRtpDiqltSE.png" to "Black Mage",
                "https://img.finalfantasyxiv.com/lds/h/t/HK0jQ1y7YV9qm30cxGOVev6Cck.png" to "Thaumaturge",
                "https://img.finalfantasyxiv.com/lds/h/h/4ghjpyyuNelzw1Bl0sM_PBA_FE.png" to "Summoner",
                "https://img.finalfantasyxiv.com/lds/h/e/VYP1LKTDpt8uJVvUT7OKrXNL9E.png" to "Arcanist",
                "https://img.finalfantasyxiv.com/lds/h/q/s3MlLUKmRAHy0pH57PnFStHmIw.png" to "Red Mage",
                "https://img.finalfantasyxiv.com/lds/h/p/jdV3RRKtWzgo226CC09vjen5sk.png" to "Blue Mage",

                "https://img.finalfantasyxiv.com/lds/h/v/YCN6F-xiXf03Ts3pXoBihh2OBk.png" to "Carpenter",
                "https://img.finalfantasyxiv.com/lds/h/5/EEHVV5cIPkOZ6v5ALaoN5XSVRU.png" to "Blacksmith",
                "https://img.finalfantasyxiv.com/lds/h/G/Rq5wcK3IPEaAB8N-T9l6tBPxCY.png" to "Armorer",
                "https://img.finalfantasyxiv.com/lds/h/L/LbEjgw0cwO_2gQSmhta9z03pjM.png" to "Goldsmith",
                "https://img.finalfantasyxiv.com/lds/h/b/ACAcQe3hWFxbWRVPqxKj_MzDiY.png" to "Leatherworker",
                "https://img.finalfantasyxiv.com/lds/h/X/E69jrsOMGFvFpCX87F5wqgT_Vo.png" to "Weaver",
                "https://img.finalfantasyxiv.com/lds/h/C/bBVQ9IFeXqjEdpuIxmKvSkqalE.png" to "Alchemist",
                "https://img.finalfantasyxiv.com/lds/h/m/1kMI2v_KEVgo30RFvdFCyySkFo.png" to "Culinarian",

                "https://img.finalfantasyxiv.com/lds/h/A/aM2Dd6Vo4HW_UGasK7tLuZ6fu4.png" to "Miner",
                "https://img.finalfantasyxiv.com/lds/h/I/jGRnjIlwWridqM-mIPNew6bhHM.png" to "Botanist",
                "https://img.finalfantasyxiv.com/lds/h/I/jGRnjIlwWridqM-mIPNew6bhHM.png" to "Fisher"
            )

            return@coroutineScope Character(
                classJob.getValue(activeClassJob.await()),
                activeClassJobLevel.await(),
                avatar.await(),
                bio.await(),
                freeCompany.await(),
                grandCompany.await(),
                grandCompanyRank.await(),
                guardianDeity.await(),
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
                town.await()
            )
        }
    }
}
