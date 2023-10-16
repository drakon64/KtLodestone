package cloud.drakon.ktlodestone.character.profile

import cloud.drakon.ktlodestone.character.ActiveClassJob
import cloud.drakon.ktlodestone.character.Discipline
import cloud.drakon.ktlodestone.character.Guild
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyRank
import cloud.drakon.ktlodestone.character.profile.gearset.GearSet
import cloud.drakon.ktlodestone.character.profile.gearset.Glamour
import cloud.drakon.ktlodestone.character.profile.gearset.Item
import cloud.drakon.ktlodestone.iconlayers.IconLayers
import cloud.drakon.ktlodestone.selectors.character.profile.AttributesSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileMaps
import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileSelectors
import cloud.drakon.ktlodestone.selectors.character.profile.GearSetSelectors
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

internal suspend fun scrapeCharacterProfile(response: String) = coroutineScope {
    Jsoup.parse(response).let {
        val classJob = async {
            CharacterProfileMaps.CLASS_JOB_MAP.getValue(
                it.select(CharacterProfileSelectors.ACTIVE_CLASSJOB)
                    .attr(CharacterProfileSelectors.ACTIVE_CLASSJOB_ATTR)
            )
        }

        val level = async {
            CharacterProfileSelectors.ACTIVE_CLASSJOB_LEVEL_REGEX.find(
                it.select(CharacterProfileSelectors.ACTIVE_CLASSJOB_LEVEL).text()
            )!!.value.toByte()
        }

        val disciple = async {
            CharacterProfileMaps.DISCIPLINE_MAP.getValue(classJob.await())
        }

        val activeClassJob = async {
            ActiveClassJob(
                classJob.await(),
                level.await(),
                disciple.await()
            )
        }

        val classJobMap = async {
            it.select(CharacterProfileSelectors.CLASSJOB_CLEARFIX)
                .flatMap {
                    it.select(CharacterProfileSelectors.CLASSJOB_ENTRIES)
                }.flatMap {
                    it.select(CharacterProfileSelectors.CLASSJOB)
                }.associate {
                    val classJob = CharacterProfileMaps.CLASS_JOB_MAP.getValue(
                        it.select(CharacterProfileSelectors.CLASSJOB_ICON)
                            .attr(CharacterProfileSelectors.CLASSJOB_ICON_ATTR)
                    )

                    val level = it.text().toByte()

                    classJob to level
                }
        }

        val avatar = async {
            it.select(CharacterProfileSelectors.AVATAR)
                .attr(CharacterProfileSelectors.AVATAR_ATTR)
        }

        val bio = async {
            it.select(CharacterProfileSelectors.BIO).text()
        }

        val freeCompany = async {
            it.select(CharacterProfileSelectors.FREE_COMPANY).first()?.let {
                val freeCompanyName = async {
                    it.text()
                }

                val freeCompanyId = async {
                    it.attr(CharacterProfileSelectors.FREE_COMPANY_ID_ATTR)
                        .split("/")[3]
                }

                val freeCompanyIconLayers = async {
                    val bottom = async {
                        it.select(CharacterProfileSelectors.FREE_COMPANY_BOTTOM_ICON_LAYER)
                            .attr(CharacterProfileSelectors.FREE_COMPANY_ICON_LAYER_ATTR)
                    }

                    val middle = async {
                        it.select(CharacterProfileSelectors.FREE_COMPANY_MIDDLE_ICON_LAYER)
                            .attr(CharacterProfileSelectors.FREE_COMPANY_ICON_LAYER_ATTR)
                    }

                    val top = async {
                        it.select(CharacterProfileSelectors.FREE_COMPANY_TOP_ICON_LAYER)
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
                    it.select(CharacterProfileSelectors.GRAND_COMPANY)
                        .text()
                        .split("/")[0]
                        .trim()
                        .replace(" ", "_")
                        .uppercase()
                )
            }

            val grandCompanyRank = async {
                GrandCompanyRank.valueOf(
                    it.select(CharacterProfileSelectors.GRAND_COMPANY)
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
                it.select(CharacterProfileSelectors.GUARDIAN_NAME).text()
            )
        }

        val name = async {
            it.select(CharacterProfileSelectors.NAME).text()
        }

        val nameday = async {
            it.select(CharacterProfileSelectors.NAMEDAY).text()
        }

        val gearSet = async {
            val mainHand = async {
                getGearSetItem(it, GearSetSelectors(0))!! // A character always has a main hand item
            }

            val offHand = async {
                getGearSetItem(it, GearSetSelectors(1))
            }

            val head = async {
                getGearSetItem(it, GearSetSelectors(2))
            }

            val body = async {
                getGearSetItem(it, GearSetSelectors(3))
            }

            val hands = async {
                getGearSetItem(it, GearSetSelectors(4))
            }

            val legs = async {
                getGearSetItem(it, GearSetSelectors(6))
            }

            val feet = async {
                getGearSetItem(it, GearSetSelectors(7))
            }

            val earrings = async {
                getGearSetItem(it, GearSetSelectors(8))
            }

            val necklace = async {
                getGearSetItem(it, GearSetSelectors(9))
            }

            val bracelets = async {
                getGearSetItem(it, GearSetSelectors(10))
            }

            val ring1 = async {
                getGearSetItem(it, GearSetSelectors(11))
            }

            val ring2 = async {
                getGearSetItem(it, GearSetSelectors(12))
            }

            val soulCrystal = async {
                getGearSetItem(it, GearSetSelectors(13))
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
            it.select(CharacterProfileSelectors.PORTRAIT)
                .attr(CharacterProfileSelectors.PORTRAIT_ATTR)
        }

        val pvpTeam = async {
            it.select(CharacterProfileSelectors.PVP_TEAM).first()?.let {
                val pvpTeamName = async {
                    it.text()
                }

                val pvpTeamId = async {
                    it.attr(CharacterProfileSelectors.PVP_TEAM_ID_ATTR)
                        .split("/")[3]
                }

                val pvpTeamIconLayers = async {
                    val bottom = async {
                        it.select(CharacterProfileSelectors.PVP_TEAM_BOTTOM_ICON_LAYER)
                            .attr(CharacterProfileSelectors.PVP_TEAM_ICON_LAYER_ATTR)
                    }

                    val middle = async {
                        it.select(CharacterProfileSelectors.PVP_TEAM_MIDDLE_ICON_LAYER)
                            .attr(CharacterProfileSelectors.PVP_TEAM_ICON_LAYER_ATTR)
                    }

                    val top = async {
                        it.select(CharacterProfileSelectors.PVP_TEAM_TOP_ICON_LAYER)
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
            it.select(CharacterProfileSelectors.RACE_CLAN_GENDER).html()
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
                it.select(CharacterProfileSelectors.WORLD)
                    .text()
                    .split("[")[0]
                    .trim()
            )
        }

        val dataCenter = async {
            DataCenter.valueOf(
                it.select(CharacterProfileSelectors.WORLD)
                    .text()
                    .split("[")[1]
                    .replace("]", "")
            )
        }

        val region = async {
            CharacterProfileMaps.REGION_MAP.getValue(dataCenter.await())
        }

        val title = async {
            it.select(CharacterProfileSelectors.TITLE).text()
        }

        val town = async {
            Town.valueOf(
                it.select(CharacterProfileSelectors.TOWN)
                    .text()
                    .replace(" ", "_")
                    .uppercase()
            )
        }

        val attributes = async {
            val strength = async {
                it.select(AttributesSelectors.STRENGTH).text().toShort()
            }

            val dexterity = async {
                it.select(AttributesSelectors.DEXTERITY).text().toShort()
            }

            val vitality = async {
                it.select(AttributesSelectors.VITALITY).text().toShort()
            }

            val intelligence = async {
                it.select(AttributesSelectors.INTELLIGENCE).text().toShort()
            }

            val mind = async {
                it.select(AttributesSelectors.MIND).text().toShort()
            }

            val criticalHitRate = async {
                it.select(AttributesSelectors.CRITICAL_HIT_RATE).text().toShort()
            }

            val determination = async {
                it.select(AttributesSelectors.DETERMINATION).text().toShort()
            }

            val directHitRate = async {
                it.select(AttributesSelectors.DIRECT_HIT_RATE).text().toShort()
            }

            val defense = async {
                it.select(AttributesSelectors.DEFENSE).text().toShort()
            }

            val magicDefense = async {
                it.select(AttributesSelectors.MAGIC_DEFENSE).text().toShort()
            }

            val attackPower = async {
                it.select(AttributesSelectors.ATTACK_POWER).text().toShort()
            }

            val skillSpeed = async {
                it.select(AttributesSelectors.SKILL_SPEED).text().toShort()
            }

            val attackMagicPotency = async {
                it.select(AttributesSelectors.ATTACK_MAGIC_POTENCY).text().toShort()
            }

            val healingMagicPotency = async {
                it.select(AttributesSelectors.HEALING_MAGIC_POTENCY).text().toShort()
            }

            val spellSpeed = async {
                it.select(AttributesSelectors.SPELL_SPEED).text().toShort()
            }

            val tenacity = async {
                it.select(AttributesSelectors.TENACITY).text().toShort()
            }

            val piety = async {
                it.select(AttributesSelectors.PIETY).text().toShort()
            }

            val hp = async {
                it.select(AttributesSelectors.HP).text().toInt()
            }

            val cp = async {
                if (disciple.await() == Discipline.DISCIPLE_OF_THE_HAND) {
                    it.select(AttributesSelectors.CP_GP).text().toShort()
                } else null
            }

            val gp = async {
                if (disciple.await() == Discipline.DISCIPLE_OF_THE_LAND) {
                    it.select(AttributesSelectors.CP_GP).text().toShort()
                } else null
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
                cp.await(),
                gp.await(),
            )
        }

        CharacterProfile(
            activeClassJob.await(),
            classJobMap.await(),
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
            attributes.await()
        )
    }
}

private suspend fun getGearSetItem(
    document: Document,
    selector: GearSetSelectors,
) = coroutineScope {
    document.select(selector.ITEM).first()?.let {
        val name = async {
            it.select(GearSetSelectors.NAME_SELECTOR).text().replace("", "")
        }

        val dbLink = async {
            "https://eu.finalfantasyxiv.com" +
                    it.select(GearSetSelectors.DB_LINK)
                        .attr(GearSetSelectors.DB_LINK_ATTR)
        }

        val glamour: Deferred<Glamour?>
        val dye: Deferred<String?>
        val materia: Deferred<List<String>>
        val creatorName: Deferred<String?>
        val hq: Deferred<Boolean>

        if (selector.icon != 13.toByte()) {
            glamour = async {
                it.select(GearSetSelectors.GLAMOUR).first()?.let {
                    it.select(GearSetSelectors.GLAMOUR_NAME).let {
                        val name = async {
                            it.text()
                        }

                        val dbLink = async {
                            "https://eu.finalfantasyxiv.com" +
                                    it.select(GearSetSelectors.GLAMOUR_DB_LINK)
                                        .attr(GearSetSelectors.GLAMOUR_DB_LINK_ATTR)
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
                        add(GearSetSelectors.MATERIA_REGEX.find(it)!!.value)
                    }?.let {
                        document.select(selector.MATERIA_2).first()?.html()?.let {
                            add(GearSetSelectors.MATERIA_REGEX.find(it)!!.value)
                        }
                    }?.let {
                        document.select(selector.MATERIA_3).first()?.html()?.let {
                            add(GearSetSelectors.MATERIA_REGEX.find(it)!!.value)
                        }
                    }?.let {
                        document.select(selector.MATERIA_4).first()?.html()?.let {
                            add(GearSetSelectors.MATERIA_REGEX.find(it)!!.value)
                        }
                    }?.let {
                        document.select(selector.MATERIA_5).first()?.html()?.let {
                            add(GearSetSelectors.MATERIA_REGEX.find(it)!!.value)
                        }
                    }
                }
            }

            creatorName = async {
                it.select(GearSetSelectors.CREATOR_NAME).first()?.text()
            }

            hq = async {
                it.select(GearSetSelectors.NAME_SELECTOR).text().contains('')
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

            hq = async {
                false
            }
        }

        Item(
            name.await(),
            dbLink.await(),
            glamour.await(),
            dye.await(),
            materia.await(),
            creatorName.await(),
            hq.await(),
        )
    }
}
