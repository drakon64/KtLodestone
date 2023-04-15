package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.Attributes
import cloud.drakon.ktlodestone.profile.FreeCompany
import cloud.drakon.ktlodestone.profile.GrandCompany
import cloud.drakon.ktlodestone.profile.Guardian
import cloud.drakon.ktlodestone.profile.IconLayers
import cloud.drakon.ktlodestone.profile.ProfileCharacter
import cloud.drakon.ktlodestone.profile.PvpTeam
import cloud.drakon.ktlodestone.profile.Town
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object Character {
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
    suspend fun getCharacter(id: Int, attributes: Boolean = false) = coroutineScope {
        val request =
            ktorClient.get("https://eu.finalfantasyxiv.com/lodestone/character/${id}/")
        val character = when (request.status.value) {
            200 -> Jsoup.parse(request.body() as String)
            404 -> throw CharacterNotFoundException("Thrown when a character could not be found on The Lodestone.")
            else -> throw LodestoneException("Thrown when The Lodestone returns an unknown error.")
        }

        val activeClassJob = async { getActiveClassJob(character) }
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

        val freeCompany = async { getFreeCompany(character) }
        val grandCompany = async { getGrandCompany(character) }
        val guardian = async { getGuardian(character) }

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

        val pvpTeam = async { getPvpTeam(character) }

        val raceClanGender = async {
            character.select("div.character-block:nth-child(1) > div:nth-child(2) > p:nth-child(2)")
                .first() !!
                .html()
        }
        val race = async { raceRegex.find(raceClanGender.await()) !!.value }
        val clan = async { clanRegex.find(raceClanGender.await()) !!.value }
        val gender = async { genderRegex.find(raceClanGender.await()) !!.value }

        val serverDc = async {
            character.select("p.frame__chara__world").first() !!.text()
        }
        val server = async { serverRegex.find(serverDc.await()) !!.value }
        val dc = async { dcRegex.find(serverDc.await()) !!.value }

        val title = async { character.select(".frame__chara__title").first()?.text() }

        val town = async { getTown(character) }

        val profileAttributes = async {
            if (attributes) {
                getAttributes(character, activeClassJob.await())
            } else {
                null
            }
        }

        return@coroutineScope ProfileCharacter(
            activeClassJob.await(),
            activeClassJobLevel.await(),
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
            profileAttributes.await()
        )
    }

    private val activeClassJobLevelRegex = """\d+""".toRegex()

    private suspend fun getActiveClassJob(character: Document) = coroutineScope {
        val classJobUrl =
            character.select(".character__class_icon > img:nth-child(1)")
                .first() !!
                .attr("src")

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

        return@coroutineScope classJob.getValue(classJobUrl)
    }

    private val freeCompanyIdRegex = """\d+""".toRegex()

    private suspend fun getFreeCompany(character: Document) = coroutineScope {
        val freeCompany = async {
            character.select(".character__freecompany__name > h4:nth-child(2) > a:nth-child(1)")
                .first()
        }

        if (freeCompany.await() != null) {
            val freeCompanyName = async { freeCompany.await() !!.text() }
            val freeCompanyId = async {
                freeCompanyIdRegex.find(
                    freeCompany.await() !!.attr("href")
                ) !!.value
            }

            val freeCompanyIconLayerBottom = async {
                character.select("div.character__freecompany__crest > div > img:nth-child(1)")
                    .first() !!
                    .attr("src")
            }
            val freeCompanyIconLayerMiddle = async {
                character.select("div.character__freecompany__crest > div > img:nth-child(2)")
                    .first() !!
                    .attr("src")
            }
            val freeCompanyIconLayerTop = async {
                character.select("div.character__freecompany__crest > div > img:nth-child(3)")
                    .first() !!
                    .attr("src")
            }

            return@coroutineScope FreeCompany(
                name = freeCompanyName.await(),
                id = freeCompanyId.await(),
                iconLayers = IconLayers(
                    bottom = freeCompanyIconLayerBottom.await(),
                    middle = freeCompanyIconLayerMiddle.await(),
                    top = freeCompanyIconLayerTop.await()
                )
            )
        } else {
            return@coroutineScope null
        }
    }

    private val grandCompanyNameRegex = """\w+""".toRegex()
    private val grandCompanyRankRegex = """(?<=\/ ).*""".toRegex()

    private suspend fun getGrandCompany(character: Document) = coroutineScope {
        val grandCompany = async {
            character.select("div.character-block:nth-child(4) > div:nth-child(2) > p:nth-child(2)")
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

            return@coroutineScope GrandCompany(
                name = grandCompanyName.await(),
                rank = grandCompanyRank.await(),
                icon = grandCompanyIcon.await()
            )
        } else {
            return@coroutineScope null
        }
    }

    private suspend fun getGuardian(character: Document) = coroutineScope {
        val guardianName = async {
            character.select("p.character-block__name:nth-child(4)").first() !!.text()
        }
        val guardianIcon = async {
            character.select("#character > div.character__content.selected > div.character__profile.clearfix > div.character__profile__data > div:nth-child(1) > div > div:nth-child(2) > img")
                .first() !!
                .attr("src")
        }

        return@coroutineScope Guardian(
            name = guardianName.await(), icon = guardianIcon.await()
        )
    }

    private val pvpTeamIdRegex = """/lodestone/pvpteam/(.*?)/""".toRegex()

    private suspend fun getPvpTeam(character: Document) = coroutineScope {
        val pvpTeam = async {
            character.select(".character__pvpteam__name > h4:nth-child(2) > a:nth-child(1)")
                .first()
        }

        if (pvpTeam.await() != null) {
            val pvpTeamId = async {
                pvpTeamIdRegex.find(
                    pvpTeam.await() !!.attr("href")
                ) !!.groups[1] !!.value
            }
            val pvpTeamName = async { pvpTeam.await() !!.text() }

            val pvpTeamIconLayerBottom = async {
                character.select(".character__pvpteam__crest__image img:nth-child(1)")
                    .first() !!
                    .attr("src")
            }
            val pvpTeamIconLayerMiddle = async {
                character.select(".character__pvpteam__crest__image img:nth-child(2)")
                    .first() !!
                    .attr("src")
            }
            val pvpTeamIconLayerTop = async {
                character.select(".character__pvpteam__crest__image img:nth-child(3)")
                    .first() !!
                    .attr("src")
            }

            return@coroutineScope PvpTeam(
                name = pvpTeamName.await(),
                id = pvpTeamId.await(),
                iconLayers = IconLayers(
                    bottom = pvpTeamIconLayerBottom.await(),
                    middle = pvpTeamIconLayerMiddle.await(),
                    top = pvpTeamIconLayerTop.await()
                )
            )
        } else {
            return@coroutineScope null
        }
    }

    private suspend fun getTown(character: Document) = coroutineScope {
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

        return@coroutineScope Town(name = townName.await(), icon = townIcon.await())
    }
}

private suspend fun getAttributes(character: Document, activeClassJob: String) =
    coroutineScope {
        val strength = async {
            character.select("table.character__param__list:nth-child(2) tr:nth-child(1) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }
        val dexterity = async {
            character.select("table.character__param__list:nth-child(2) tr:nth-child(2) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }
        val vitality = async {
            character.select("table.character__param__list:nth-child(2) tr:nth-child(3) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }
        val intelligence = async {
            character.select("table.character__param__list:nth-child(2) tr:nth-child(4) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }
        val mind = async {
            character.select("table.character__param__list:nth-child(2) tr:nth-child(5) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }

        val criticalHitRate = async {
            character.select("table.character__param__list:nth-child(4) tr:nth-child(1) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }
        val determination = async {
            character.select("table.character__param__list:nth-child(4) tr:nth-child(2) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }
        val directHitRate = async {
            character.select("table.character__param__list:nth-child(4) tr:nth-child(3) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }

        val defense = async {
            character.select("table.character__param__list:nth-child(6) tr:nth-child(1) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }
        val magicDefense = async {
            character.select("table.character__param__list:nth-child(6) tr:nth-child(2) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }

        val attackPower = async {
            character.select("table.character__param__list:nth-child(8) tr:nth-child(1) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }
        val skillSpeed = async {
            character.select("table.character__param__list:nth-child(8) tr:nth-child(2) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }

        val attackMagicPotency = async {
            character.select("table.character__param__list:nth-child(10) tr:nth-child(1) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }
        val healingMagicPotency = async {
            character.select("table.character__param__list:nth-child(10) tr:nth-child(2) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }
        val spellSpeed = async {
            character.select("table.character__param__list:nth-child(10) tr:nth-child(3) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }

        val tenacity = async {
            character.select("table.character__param__list:nth-child(12) tr:nth-child(1) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }
        val piety = async {
            character.select("table.character__param__list:nth-child(12) tr:nth-child(2) > td:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }

        val hp = async {
            character.select(".character__param > ul:nth-child(1) > li:nth-child(1) > div:nth-child(1) > span:nth-child(2)")
                .first() !!
                .text()
                .toInt()
        }

        suspend fun getCpGp(): Short = coroutineScope {
            return@coroutineScope character.select(".character__param > ul:nth-child(1) > li:nth-child(2) > div:nth-child(1) > span:nth-child(2)")
                .first() !!
                .text()
                .toShort()
        }

        val cp = async {
            when (activeClassJob) {
                "Carpenter", "Blacksmith", "Armorer", "Goldsmith", "Leatherworker", "Weaver", "Alchemist", "Culinarian" -> getCpGp()
                else -> null
            }
        }
        val gp = async {
            when (activeClassJob) {
                "Miner", "Botanist", "Fisher" -> getCpGp()
                else -> null
            }
        }
        val mp = async {
            if (cp.await() == null && gp.await() == null) {
                10000
            } else {
                null
            }
        }

        return@coroutineScope Attributes(
            strength.await(),
            dexterity.await(),
            vitality.await(),
            intelligence.await(),
            mind.await(),
            criticalHitRate.await(),
            determination.await(),
            directHitRate.await(),
            defense.await(),
            magicDefense.await(),
            attackPower.await(),
            skillSpeed.await(),
            attackMagicPotency.await(),
            healingMagicPotency.await(),
            spellSpeed.await(),
            tenacity.await(),
            piety.await(),
            hp.await(),
            mp.await(),
            cp.await(),
            gp.await()
        )
    }
