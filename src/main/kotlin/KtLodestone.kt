package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.ClassJobException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.Character
import cloud.drakon.ktlodestone.profile.freecompany.FreeCompany
import cloud.drakon.ktlodestone.profile.freecompany.FreeCompanyIconLayers
import cloud.drakon.ktlodestone.profile.guardiandeity.GuardianDeity
import cloud.drakon.ktlodestone.profile.pvpteam.PvpTeam
import cloud.drakon.ktlodestone.profile.pvpteam.PvpTeamIconLayers
import cloud.drakon.ktlodestone.profile.town.Town
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.java.Java
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

object KtLodestone {
    private val ktorClient = HttpClient(Java)

    object Character {
        private val activeClassJobLevelRegex = """\d+""".toRegex()
        private val serverRegex = """\S+""".toRegex()
        private val dcRegex = """(?<=\[)\w+(?=\])""".toRegex()

        /**
         * Gets a character from The Lodestone
         * @param id The Lodestone character ID
         * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
         * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
         */
        suspend fun getCharacter(id: Int): cloud.drakon.ktlodestone.profile.Character =
            coroutineScope {
                val request =
                    ktorClient.get("https://eu.finalfantasyxiv.com/lodestone/character/${id}/")
                val character = when (request.status.value) {
                    200 -> Jsoup.parse(request.body() as String)
                    404 -> throw CharacterNotFoundException("Thrown when a character could not be found on The Lodestone.")
                    else -> throw LodestoneException("Thrown when The Lodestone returns an unknown error.")
                }

                val activeClassJob = async {
                    when (character.select(".character__class_icon > img:nth-child(1)")
                        .first() !!
                        .attr("src")) {

                        // Classes
                        "https://img.finalfantasyxiv.com/lds/h/U/F5JzG9RPIKFSogtaKNBk455aYA.png" -> "Gladiator"
                        "https://img.finalfantasyxiv.com/lds/h/N/St9rjDJB3xNKGYg-vwooZ4j6CM.png" -> "Marauder"
                        "https://img.finalfantasyxiv.com/lds/h/V/iW7IBKQ7oglB9jmbn6LwdZXkWw.png" -> "Pugilist"
                        "https://img.finalfantasyxiv.com/lds/h/y/wdwVVcptybfgSruoh8R344y_GA.png" -> "Rogue"
                        "https://img.finalfantasyxiv.com/lds/h/4/IM3PoP6p06GqEyReygdhZNh7fU.png" -> "Thaumaturge"

                        // Jobs
                        "https://img.finalfantasyxiv.com/lds/h/1/erCgjnMSiab4LiHpWxVc-tXAqk.png" -> "Astrologian"
                        "https://img.finalfantasyxiv.com/lds/h/F/KWI-9P3RX_Ojjn_mwCS2N0-3TI.png" -> "Bard"
                        "https://img.finalfantasyxiv.com/lds/h/P/V01m8YRBYcIs5vgbRtpDiqltSE.png" -> "Black Mage"
                        "https://img.finalfantasyxiv.com/lds/h/p/jdV3RRKtWzgo226CC09vjen5sk.png" -> "Blue Mage"
                        "https://img.finalfantasyxiv.com/lds/h/t/HK0jQ1y7YV9qm30cxGOVev6Cck.png" -> "Dancer"
                        "https://img.finalfantasyxiv.com/lds/h/l/5CZEvDOMYMyVn2td9LZigsgw9s.png" -> "Dark Knight"
                        "https://img.finalfantasyxiv.com/lds/h/m/gX4OgBIHw68UcMU79P7LYCpldA.png" -> "Dragoon"
                        "https://img.finalfantasyxiv.com/lds/h/8/hg8ofSSOKzqng290No55trV4mI.png" -> "Gunbreaker"
                        "https://img.finalfantasyxiv.com/lds/h/E/vmtbIlf6Uv8rVp2YFCWA25X0dc.png" -> "Machinist"
                        "https://img.finalfantasyxiv.com/lds/h/7/cLlXUaeMPJDM2nBhIeM-uDmPzM.png" -> "Reaper"
                        "https://img.finalfantasyxiv.com/lds/h/q/s3MlLUKmRAHy0pH57PnFStHmIw.png" -> "Red Mage"
                        "https://img.finalfantasyxiv.com/lds/h/g/_oYApASVVReLLmsokuCJGkEpk0.png" -> "Sage"
                        "https://img.finalfantasyxiv.com/lds/h/m/KndG72XtCFwaq1I1iqwcmO_0zc.png" -> "Samurai"
                        "https://img.finalfantasyxiv.com/lds/h/7/WdFey0jyHn9Nnt1Qnm-J3yTg5s.png" -> "Scholar"
                        "https://img.finalfantasyxiv.com/lds/h/h/4ghjpyyuNelzw1Bl0sM_PBA_FE.png" -> "Summoner"
                        "https://img.finalfantasyxiv.com/lds/h/7/i20QvSPcSQTybykLZDbQCgPwMw.png" -> "White Mage"

                        else -> throw ClassJobException("Unknown class/job")
                    }
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
                            id = freeCompanyId.await() !!,
                            iconLayers = freeCompanyIconLayers.await() !!
                        )
                    } else {
                        null
                    }
                }

                val grandCompany = async {
                    character.select("div.character-block:nth-child(4) > div:nth-child(2) > p:nth-child(2)")
                        .first()
                        ?.text()
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
                        name = guardianDeityName.await(),
                        icon = guardianDeityIcon.await()
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
                        .text()
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

                return@coroutineScope Character(
                    activeClassJob.await(),
                    activeClassJobLevel.await(),
                    avatar.await(),
                    bio.await(),
                    freeCompany.await(),
                    grandCompany.await(),
                    guardianDeity.await(),
                    name.await(),
                    nameday.await(),
                    portrait.await(),
                    pvpTeam.await(),
                    raceClanGender.await(),
                    server.await(),
                    dc.await(),
                    title.await(),
                    town.await()
                )
            }
    }
}
