package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.Attributes
import cloud.drakon.ktlodestone.profile.GrandCompany
import cloud.drakon.ktlodestone.profile.Guardian
import cloud.drakon.ktlodestone.profile.Guild
import cloud.drakon.ktlodestone.profile.GuildType
import cloud.drakon.ktlodestone.profile.IconLayers
import cloud.drakon.ktlodestone.profile.ProfileCharacter
import cloud.drakon.ktlodestone.profile.ProfileGearSet
import cloud.drakon.ktlodestone.profile.Town
import cloud.drakon.ktlodestone.profile.gear.Gear
import cloud.drakon.ktlodestone.profile.gear.Glamour
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
     * @param attributes Return character attributes
     * @param gearSet Return character equipped gear
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    suspend fun getCharacter(
        id: Int,
        attributes: Boolean = false,
        gearSet: Boolean = false,
    ) = coroutineScope {
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
                character.select(getLodestoneCss("ACTIVE_CLASSJOB_LEVEL"))
                    .first() !!
                    .text()
            ) !!.value.toByte()
        }

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

        val profileAttributes = async {
            if (attributes) {
                Attributes.getAttributes(character)
            } else {
                null
            }
        }

        val profileGearSet = async {
            if (gearSet) {
                GearSet.getGearSet(character)
            } else {
                null
            }
        }

        ProfileCharacter(
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
            profileAttributes.await(),
            profileGearSet.await()
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
        val classJobUrl =
            character.select(getLodestoneCss("ACTIVE_CLASSJOB"))
                .first() !!
                .attr(getLodestoneCss("ACTIVE_CLASSJOB", "attribute"))

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

        classJob.getValue(classJobUrl)
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

    private object Attributes {
        private val lodestoneCssSelectors = Json.parseToJsonElement(
            this::class.java.classLoader.getResource("lodestone-css-selectors/profile/attributes.json") !!
                .readText()
        )

        suspend fun getAttributes(character: Document) = coroutineScope {
            val strength = async {
                getAttributeCss(character, "STRENGTH").toShort()
            }
            val dexterity = async {
                getAttributeCss(character, "DEXTERITY").toShort()
            }
            val vitality = async {
                getAttributeCss(character, "VITALITY").toShort()
            }
            val intelligence = async {
                getAttributeCss(character, "INTELLIGENCE").toShort()
            }
            val mind = async {
                getAttributeCss(character, "MIND").toShort()
            }

            val criticalHitRate = async {
                getAttributeCss(character, "CRITICAL_HIT_RATE").toShort()
            }
            val determination = async {
                getAttributeCss(character, "DETERMINATION").toShort()
            }
            val directHitRate = async {
                getAttributeCss(character, "DIRECT_HIT_RATE").toShort()
            }

            val defense = async {
                getAttributeCss(character, "DEFENSE").toShort()
            }
            val magicDefense = async {
                getAttributeCss(character, "MAGIC_DEFENSE").toShort()
            }

            val attackPower = async {
                getAttributeCss(character, "ATTACK_POWER").toShort()
            }
            val skillSpeed = async {
                getAttributeCss(character, "SKILL_SPEED").toShort()
            }

            val attackMagicPotency = async {
                getAttributeCss(character, "ATTACK_MAGIC_POTENCY").toShort()
            }
            val healingMagicPotency = async {
                getAttributeCss(character, "HEALING_MAGIC_POTENCY").toShort()
            }
            val spellSpeed = async {
                getAttributeCss(character, "SPELL_SPEED").toShort()
            }

            val tenacity = async {
                getAttributeCss(character, "TENACITY").toShort()
            }
            val piety = async {
                getAttributeCss(character, "PIETY").toShort()
            }

            val hp = async {
                getAttributeCss(character, "HP").toInt()
            }
            val mp = async {
                getAttributeCss(character, "MP_GP_CP").toInt()
            }

            Attributes(
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
                mp.await()
            )
        }

        private suspend fun getAttributeCss(
            character: Document,
            lodestoneProperty: String,
        ) = coroutineScope {
            character.select(lodestoneCssSelectors.jsonObject[lodestoneProperty] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .text()
        }
    }

    private object GearSet {
        private val lodestoneCssSelectors = Json.parseToJsonElement(
            this::class.java.classLoader.getResource("lodestone-css-selectors/profile/gearset.json") !!
                .readText()
        )

        suspend fun getGearSet(character: Document) = coroutineScope {
            val mainHand = async {
                getGearSetCss(character, "MAINHAND") !! // Cannot be null
            }
            val offHand = async { getGearSetCss(character, "OFFHAND") }
            val head = async { getGearSetCss(character, "HEAD") }
            val body = async { getGearSetCss(character, "BODY") }
            val hands = async { getGearSetCss(character, "HANDS") }
            val legs = async { getGearSetCss(character, "LEGS") }
            val feet = async { getGearSetCss(character, "FEET") }
            val earrings = async { getGearSetCss(character, "EARRINGS") }
            val necklace = async { getGearSetCss(character, "NECKLACE") }
            val bracelets = async { getGearSetCss(character, "BRACELETS") }
            val ring1 = async { getGearSetCss(character, "RING1") }
            val ring2 = async { getGearSetCss(character, "RING2") }
            val ring = async {
                if (ring1.await() != null || ring2.await() != null) {
                    listOf(ring1.await(), ring2.await())
                } else {
                    null
                }
            }
            val soulCrystal = async { getSoulCrystalCss(character) }

            return@coroutineScope ProfileGearSet(
                mainHand.await(),
                offHand.await(),
                head.await(),
                body.await(),
                hands.await(),
                legs.await(),
                feet.await(),
                earrings.await(),
                necklace.await(),
                bracelets.await(),
                ring.await(),
                soulCrystal.await()
            )
        }

        private suspend fun getGearSetCss(character: Document, gear: String) =
            coroutineScope {
                val css = lodestoneCssSelectors.jsonObject[gear] !!

                val name = async {
                    character.select(css.jsonObject["NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                        .first()
                        ?.text()
                }
                val dbLink = async {
                    val link =
                        character.select(css.jsonObject["DB_LINK"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                            .first()
                            ?.attr("href")

                    if (link != null) {
                        "https://eu.finalfantasyxiv.com${link}"
                    } else {
                        null
                    }
                }

                val mirageName = async {
                    character.select(css.jsonObject["MIRAGE_NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                        .first()
                        ?.text()
                }
                val mirageDbLink = async {
                    val link =
                        character.select(css.jsonObject["MIRAGE_DB_LINK"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                            .first()
                            ?.attr("href")

                    if (link != null) {
                        "https://eu.finalfantasyxiv.com${link}"
                    } else {
                        null
                    }
                }
                val glamour =
                    if (mirageName.await() != null && mirageDbLink.await() != null) {
                        Glamour(
                            mirageName.await() !!, mirageDbLink.await() !!
                        )
                    } else {
                        null
                    }

                val stain = async {
                    character.select(css.jsonObject["STAIN"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                        .first()
                        ?.text()
                }

                val materia = async { getMateriaCss(character, gear) }

                val creatorName = async {
                    character.select(css.jsonObject["CREATOR_NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                        .first()
                        ?.text()
                }
                val hq = async {
                    if (name.await() != null) {
                        name.await() !!.endsWith("\uE03C") // HQ symbol
                    } else {
                        null
                    }
                }

                return@coroutineScope if (name.await() != null) {
                    Gear(
                        name.await() !!.replace("\uE03C", ""), // Strip the HQ symbol
                        dbLink.await() !!,
                        glamour,
                        stain.await(),
                        materia.await(),
                        creatorName.await(),
                        hq.await() !!
                    )
                } else {
                    null
                }
            }

        private val materiaRegex = """.*(?=<br>)""".toRegex()

        private suspend fun getMateriaCss(character: Document, gear: String) =
            coroutineScope {
                val css = lodestoneCssSelectors.jsonObject[gear] !!

                val materia1 = async {
                    val materia =
                        character.select(css.jsonObject["MATERIA_1"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                            .first()
                            ?.html()

                    if (materia != null) {
                        materiaRegex.find(materia) !!.value
                    } else {
                        null
                    }
                }
                val materia2 = async {
                    val materia =
                        character.select(css.jsonObject["MATERIA_2"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                            .first()
                            ?.html()

                    if (materia != null) {
                        materiaRegex.find(materia) !!.value
                    } else {
                        null
                    }
                }
                val materia3 = async {
                    val materia =
                        character.select(css.jsonObject["MATERIA_3"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                            .first()
                            ?.html()

                    if (materia != null) {
                        materiaRegex.find(materia) !!.value
                    } else {
                        null
                    }
                }
                val materia4 = async {
                    val materia =
                        character.select(css.jsonObject["MATERIA_4"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                            .first()
                            ?.html()

                    if (materia != null) {
                        materiaRegex.find(materia) !!.value
                    } else {
                        null
                    }
                }
                val materia5 = async {
                    val materia =
                        character.select(css.jsonObject["MATERIA_5"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                            .first()
                            ?.html()

                    if (materia != null) {
                        materiaRegex.find(materia) !!.value
                    } else {
                        null
                    }
                }
                val materiaList = mutableListOf<String>()

                for (i in arrayOf(
                    materia1, materia2, materia3, materia4, materia5
                )) {
                    val materia = i.await()

                    if (materia != null) {
                        materiaList.add(materia)
                    }
                }

                if (materiaList.isNotEmpty()) {
                    materiaList.toList()
                } else {
                    null
                }
            }

        private suspend fun getSoulCrystalCss(character: Document) = coroutineScope {
            return@coroutineScope character.select(lodestoneCssSelectors.jsonObject["SOULCRYSTAL"] !!.jsonObject["NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first()
                ?.text()
        }
    }
}
