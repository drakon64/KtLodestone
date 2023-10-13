package cloud.drakon.ktlodestone.character.profile

import cloud.drakon.ktlodestone.character.ActiveClassJob
import cloud.drakon.ktlodestone.character.Guild
import cloud.drakon.ktlodestone.character.classjob.ClassJob
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyRank
import cloud.drakon.ktlodestone.character.profile.gearset.GearSet
import cloud.drakon.ktlodestone.character.profile.gearset.Glamour
import cloud.drakon.ktlodestone.character.profile.gearset.Item
import cloud.drakon.ktlodestone.iconlayers.IconLayers
import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileMaps
import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.BodySelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.BraceletsSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.EarringsSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.FeetSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.GearSetSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.HandsSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.HeadSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.LegsSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.MainHandSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.NecklaceSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.OffHandSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.Ring1Selectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.Ring2Selectors
import cloud.drakon.ktlodestone.selectors.character.profile.gearset.SoulCrystalSelectors
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

internal suspend fun scrapeCharacterProfile(response: String) = coroutineScope {
    val document = Jsoup.parse(response)

    val activeClassJob = async {
        val classJob = async {
            CharacterProfileMaps.CLASS_JOB_MAP.getValue(
                document.select(CharacterProfileSelectors.ACTIVE_CLASSJOB)
                    .attr(CharacterProfileSelectors.ACTIVE_CLASSJOB_ATTR)
            )
        }

        val level = async {
            CharacterProfileSelectors.ACTIVE_CLASSJOB_LEVEL_REGEX.find(
                document.select(CharacterProfileSelectors.ACTIVE_CLASSJOB_LEVEL).text()
            )!!.value.toByte()
        }

        ActiveClassJob(classJob.await(), level.await())
    }

    val classJob = async {
        with(mutableMapOf<ClassJob, Byte>()) {
            document.select(CharacterProfileSelectors.CLASSJOB_CLEARFIX).forEach {
                it.select(CharacterProfileSelectors.CLASSJOB_ENTRIES).forEach {
                    it.select(CharacterProfileSelectors.CLASSJOB).forEach {
                        val classJob = async {
                            CharacterProfileMaps.CLASS_JOB_MAP.getValue(
                                it.select(CharacterProfileSelectors.CLASSJOB_ICON)
                                    .attr(CharacterProfileSelectors.CLASSJOB_ICON_ATTR)
                            )
                        }

                        val level = async {
                            it.text().toByte()
                        }

                        this[classJob.await()] = level.await()
                    }
                }
            }

            this.toMap()
        }
    }

    val avatar = async {
        document.select(CharacterProfileSelectors.AVATAR)
            .attr(CharacterProfileSelectors.AVATAR_ATTR)
    }

    val bio = async {
        document.select(CharacterProfileSelectors.BIO).text()
    }

    val freeCompany = async {
        document.select(CharacterProfileSelectors.FREE_COMPANY).first()?.let {
            val freeCompanyName = async {
                it.text()
            }

            val freeCompanyId = async {
                it.attr(CharacterProfileSelectors.FREE_COMPANY_ID_ATTR)
                    .split("/")[3]
            }

            val freeCompanyIconLayers = async {
                val bottom = async {
                    document.select(CharacterProfileSelectors.FREE_COMPANY_BOTTOM_ICON_LAYER)
                        .attr(CharacterProfileSelectors.FREE_COMPANY_ICON_LAYER_ATTR)
                }

                val middle = async {
                    document.select(CharacterProfileSelectors.FREE_COMPANY_MIDDLE_ICON_LAYER)
                        .attr(CharacterProfileSelectors.FREE_COMPANY_ICON_LAYER_ATTR)
                }

                val top = async {
                    document.select(CharacterProfileSelectors.FREE_COMPANY_TOP_ICON_LAYER)
                        .attr(CharacterProfileSelectors.FREE_COMPANY_ICON_LAYER_ATTR)
                }

                IconLayers(bottom.await(), middle.await(), top.await())
            }

            Guild(
                freeCompanyName.await(),
                freeCompanyId.await(),
                freeCompanyIconLayers.await()
            )
        }
    }

    val grandCompany = async {
        val grandCompanyName = async {
            GrandCompanyName.valueOf(
                document.select(CharacterProfileSelectors.GRAND_COMPANY)
                    .text()
                    .split("/")[0]
                    .trim()
                    .replace(" ", "_")
                    .uppercase()
            )
        }

        val grandCompanyRank = async {
            GrandCompanyRank.valueOf(
                document.select(CharacterProfileSelectors.GRAND_COMPANY)
                    .text()
                    .split("/")[1]
                    .trim()
                    .replace(" ", "_")
                    .uppercase()
            )
        }

        GrandCompany(grandCompanyName.await(), grandCompanyRank.await())
    }

    val guardian = async {
        CharacterProfileMaps.GUARDIAN_MAP.getValue(
            document.select(CharacterProfileSelectors.GUARDIAN_NAME).text()
        )
    }

    val name = async {
        document.select(CharacterProfileSelectors.NAME).text()
    }

    val nameday = async {
        document.select(CharacterProfileSelectors.NAMEDAY).text()
    }

    val gearSet = async {
        val mainHand = async {
            getGearSetItem(
                document,
                MainHandSelectors
            )!! // A character always has a main hand item
        }

        val offHand = async {
            getGearSetItem(document, OffHandSelectors)
        }

        val head = async {
            getGearSetItem(document, HeadSelectors)
        }

        val body = async {
            getGearSetItem(document, BodySelectors)
        }

        val hands = async {
            getGearSetItem(document, HandsSelectors)
        }

        val legs = async {
            getGearSetItem(document, LegsSelectors)
        }

        val feet = async {
            getGearSetItem(document, FeetSelectors)
        }

        val earrings = async {
            getGearSetItem(document, EarringsSelectors)
        }

        val necklace = async {
            getGearSetItem(document, NecklaceSelectors)
        }

        val bracelets = async {
            getGearSetItem(document, BraceletsSelectors)
        }

        val ring1 = async {
            getGearSetItem(document, Ring1Selectors)
        }

        val ring2 = async {
            getGearSetItem(document, Ring2Selectors)
        }

        val soulCrystal = async {
            getGearSetItem(document, SoulCrystalSelectors, true)
        }

        GearSet(
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
            ring1.await(),
            ring2.await(),
            soulCrystal.await(),
        )
    }

    val portrait = async {
        document.select(CharacterProfileSelectors.PORTRAIT)
            .attr(CharacterProfileSelectors.PORTRAIT_ATTR)
    }

    val pvpTeam = async {
        document.select(CharacterProfileSelectors.PVP_TEAM).first()?.let {
            val pvpTeamName = async {
                it.text()
            }

            val pvpTeamId = async {
                it.attr(CharacterProfileSelectors.PVP_TEAM_ID_ATTR)
                    .split("/")[3]
            }

            val pvpTeamIconLayers = async {
                val bottom = async {
                    document.select(CharacterProfileSelectors.PVP_TEAM_BOTTOM_ICON_LAYER)
                        .attr(CharacterProfileSelectors.PVP_TEAM_ICON_LAYER_ATTR)
                }

                val middle = async {
                    document.select(CharacterProfileSelectors.PVP_TEAM_MIDDLE_ICON_LAYER)
                        .attr(CharacterProfileSelectors.PVP_TEAM_ICON_LAYER_ATTR)
                }

                val top = async {
                    document.select(CharacterProfileSelectors.PVP_TEAM_TOP_ICON_LAYER)
                        .attr(CharacterProfileSelectors.PVP_TEAM_ICON_LAYER_ATTR)
                }

                IconLayers(bottom.await(), middle.await(), top.await())
            }

            Guild(
                pvpTeamName.await(),
                pvpTeamId.await(),
                pvpTeamIconLayers.await()
            )
        }
    }

    val raceClanGender = async {
        document.select(CharacterProfileSelectors.RACE_CLAN_GENDER).html()
    }

    val race = async {
        CharacterProfileMaps.RACE_MAP.getValue(
            CharacterProfileSelectors.RACE_REGEX.find(
                raceClanGender.await()
            )
            !!.value
        )
    }

    val clan = async {
        Clan.valueOf(
            CharacterProfileSelectors.CLAN_REGEX.find(
                raceClanGender.await()
            )!!.value
                .replace(" ", "_")
                .uppercase()
        )
    }

    val gender = async {
        CharacterProfileMaps.GENDER_MAP.getValue(
            CharacterProfileSelectors.GENDER_REGEX.find(
                raceClanGender.await()
            )
            !!.value[0]
        )
    }

    val world = async {
        World.valueOf(
            document.select(CharacterProfileSelectors.WORLD)
                .text()
                .split("[")[0]
                .trim()
        )
    }

    val dataCenter = async {
        DataCenter.valueOf(
            document.select(CharacterProfileSelectors.WORLD)
                .text()
                .split("[")[1]
                .replace("]", "")
        )
    }

    val region = async {
        CharacterProfileMaps.REGION_MAP.getValue(dataCenter.await())
    }

    val title = async {
        document.select(CharacterProfileSelectors.TITLE).text()
    }

    val town = async {
        Town.valueOf(
            document.select(CharacterProfileSelectors.TOWN)
                .text()
                .replace(" ", "_")
                .uppercase()
        )
    }

    CharacterProfile(
        activeClassJob.await(),
        classJob.await(),
        avatar.await(),
        bio.await(),
        freeCompany.await(),
        grandCompany.await(),
        guardian.await(),
        name.await(),
        nameday.await(),
        gearSet.await(),
        portrait.await(),
        pvpTeam.await(),
        race.await(),
        clan.await(),
        gender.await(),
        world.await(),
        dataCenter.await(),
        region.await(),
        title.await(),
        town.await(),
    )
}

private suspend fun getGearSetItem(
    document: Document,
    selector: GearSetSelectors,
    soulCrystal: Boolean = false,
) = coroutineScope {
    document.select(selector.ITEM).first()?.let {
        val name = async {
            document.select(selector.NAME_SELECTOR).text()
        }

        val dbLink = async {
            "https://eu.finalfantasyxiv.com" +
                    document.select(MainHandSelectors.DB_LINK)
                        .attr(selector.DB_LINK_ATTR)
        }

        val glamour: Deferred<Glamour?>
        val dye: Deferred<String?>
        val materia: Deferred<List<String>>
        val creatorName: Deferred<String?>

        if (!soulCrystal) {
            glamour = async {
                document.select(selector.GLAMOUR).first()?.let {
                    it.select(selector.GLAMOUR_NAME).let {
                        val name = async {
                            it.text()
                        }

                        val dbLink = async {
                            "https://eu.finalfantasyxiv.com" +
                                    it.select(selector.GLAMOUR_DB_LINK)
                                        .attr(selector.GLAMOUR_DB_LINK_ATTR)
                        }

                        Glamour(name.await(), dbLink.await())
                    }
                }
            }

            dye = async {
                document.select(selector.DYE).first()?.text()
            }

            materia = async {
                buildList {
                    // Search materia in sequence and stop if we find a `null`
                    document.select(selector.MATERIA_1).first()?.html()?.let {
                        add(selector.MATERIA_REGEX.find(it)!!.value)
                    }.let {
                        document.select(selector.MATERIA_2).first()?.html()?.let {
                            add(selector.MATERIA_REGEX.find(it)!!.value)
                        }
                    }.let {
                        document.select(selector.MATERIA_3).first()?.html()?.let {
                            add(selector.MATERIA_REGEX.find(it)!!.value)
                        }
                    }.let {
                        document.select(selector.MATERIA_4).first()?.html()?.let {
                            add(selector.MATERIA_REGEX.find(it)!!.value)
                        }
                    }.let {
                        document.select(selector.MATERIA_5).first()?.html()?.let {
                            add(selector.MATERIA_REGEX.find(it)!!.value)
                        }
                    }
                }
            }

            creatorName = async {
                document.select(selector.CREATOR_NAME).first()?.text()
            }
        } else {
            glamour = async {
                null
            }

            dye = async {
                null
            }

            materia = async {
                emptyList()
            }

            creatorName = async {
                null
            }
        }

        Item(
            name.await(),
            dbLink.await(),
            glamour.await(),
            dye.await(),
            materia.await(),
            creatorName.await()
        )
    }
}
