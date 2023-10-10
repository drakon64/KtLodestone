package cloud.drakon.ktlodestone.character.profile

import cloud.drakon.ktlodestone.character.ActiveClassJob
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyRank
import cloud.drakon.ktlodestone.iconlayers.IconLayers
import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileMaps
import cloud.drakon.ktlodestone.selectors.character.profile.CharacterProfileSelectors
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

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

    val avatar = async {
        document.select(CharacterProfileSelectors.AVATAR)
            .attr(CharacterProfileSelectors.AVATAR_ATTR)
    }

    val bio = async {
        document.select(CharacterProfileSelectors.BIO).text()
    }

    val freeCompany = async {
        val freeCompanyElement =
            document.select(CharacterProfileSelectors.FREE_COMPANY).first()

        if (freeCompanyElement != null) {
            val freeCompanyName = async {
                freeCompanyElement.text()
            }

            val freeCompanyId = async {
                freeCompanyElement.attr(CharacterProfileSelectors.FREE_COMPANY_ID_ATTR)
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
        } else null
    }

    val grandCompany = async {
        val grandCompanyName = async {
            GrandCompanyName.valueOf(
                document.select(CharacterProfileSelectors.GRAND_COMPANY).text()
                    .split("/")[0].trim().replace(" ", "_").uppercase()
            )
        }

        val grandCompanyRank = async {
            GrandCompanyRank.valueOf(
                document.select(CharacterProfileSelectors.GRAND_COMPANY).text()
                    .split("/")[1].trim().replace(" ", "_").uppercase()
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

    val portrait = async {
        document.select(CharacterProfileSelectors.PORTRAIT)
            .attr(CharacterProfileSelectors.PORTRAIT_ATTR)
    }

    val pvpTeam = async {
        val pvpTeamElement = document.select(CharacterProfileSelectors.PVP_TEAM).first()

        if (pvpTeamElement != null) {
            val pvpTeamName = async {
                pvpTeamElement.text()
            }

            val pvpTeamId = async {
                pvpTeamElement.attr(CharacterProfileSelectors.PVP_TEAM_ID_ATTR)
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
        } else null
    }

    val raceClanGender = async {
        document.select(CharacterProfileSelectors.RACE_CLAN_GENDER).html()
    }

    val race = async {
        CharacterProfileMaps.RACE_MAP.getValue(
            CharacterProfileSelectors.RACE_REGEX.find(
                raceClanGender.await()
            )!!.value
        )
    }

    val clan = async {
        Clan.valueOf(
            CharacterProfileSelectors.CLAN_REGEX.find(raceClanGender.await())!!.value
                .replace(" ", "_")
                .uppercase()
        )
    }

    val gender = async {
        CharacterProfileMaps.GENDER_MAP.getValue(
            CharacterProfileSelectors.GENDER_REGEX.find(
                raceClanGender.await()
            )!!.value[0]
        )
    }

    val world = async {
        World.valueOf(
            document.select(CharacterProfileSelectors.WORLD).text().split("[")[0].trim()
        )
    }

    val dataCenter = async {
        DataCenter.valueOf(
            document.select(CharacterProfileSelectors.WORLD).text()
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
            document.select(CharacterProfileSelectors.TOWN).text()
                .replace(" ", "_")
                .uppercase()
        )
    }

    CharacterProfile(
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
        world.await(),
        dataCenter.await(),
        region.await(),
        title.await(),
        town.await(),
    )
}
