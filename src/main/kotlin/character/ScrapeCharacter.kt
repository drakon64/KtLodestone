package cloud.drakon.ktlodestone.character

import cloud.drakon.ktlodestone.IconLayers
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyRank
import cloud.drakon.ktlodestone.selectors.CharacterSelectors
import cloud.drakon.ktlodestone.selectors.CharacterSelectors.ACTIVE_CLASSJOB_LEVEL_REGEX
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

internal suspend fun scrapeCharacter(response: String) = coroutineScope {
    val document = Jsoup.parse(response)

    val activeClassJob = async {
        CharacterSelectors.CLASS_JOB_MAP.getValue(
            document.select(CharacterSelectors.ACTIVE_CLASSJOB)
                .attr(CharacterSelectors.ACTIVE_CLASSJOB_ATTR)
        )
    }

    val activeClassJobLevel = async {
        ACTIVE_CLASSJOB_LEVEL_REGEX.find(
            document.select(CharacterSelectors.ACTIVE_CLASSJOB_LEVEL).text()
        )!!.value.toByte()
    }

    val avatar = async {
        document.select(CharacterSelectors.AVATAR).attr(CharacterSelectors.AVATAR_ATTR)
    }

    val bio = async {
        document.select(CharacterSelectors.BIO).text()
    }

    val freeCompany = async {
        val freeCompanyName = async {
            document.select(CharacterSelectors.FREE_COMPANY).text()
        }

        val freeCompanyId = async {
            document.select(CharacterSelectors.FREE_COMPANY)
                .attr(CharacterSelectors.FREE_COMPANY_ID_ATTR).toInt()
        }

        val freeCompanyIconLayers = async {
            val bottom = async {
                document.select(CharacterSelectors.FREE_COMPANY_BOTTOM_ICON_LAYER)
                    .attr(CharacterSelectors.FREE_COMPANY_ICON_LAYER_ATTR)
            }

            val middle = async {
                document.select(CharacterSelectors.FREE_COMPANY_MIDDLE_ICON_LAYER)
                    .attr(CharacterSelectors.FREE_COMPANY_ICON_LAYER_ATTR)
            }

            val top = async {
                document.select(CharacterSelectors.FREE_COMPANY_TOP_ICON_LAYER)
                    .attr(CharacterSelectors.FREE_COMPANY_ICON_LAYER_ATTR)
            }

            IconLayers(bottom.await(), middle.await(), top.await())
        }

        Guild(
            freeCompanyName.await(),
            freeCompanyId.await(),
            freeCompanyIconLayers.await()
        )
    }

    val grandCompany = async {
        val grandCompanyName = async {
            GrandCompanyName.valueOf(
                document.select(CharacterSelectors.GRAND_COMPANY).text()
            )
        }

        val grandCompanyRank = async {
            GrandCompanyRank.valueOf(
                document.select(CharacterSelectors.GRAND_COMPANY).text()
            )
        }

        GrandCompany(grandCompanyName.await(), grandCompanyRank.await())
    }

    val guardian = async {
        Guardian.valueOf(document.select(CharacterSelectors.GUARDIAN_NAME).text())
    }

    val name = async {
        document.select(CharacterSelectors.NAME).text()
    }

    val nameday = async {
        document.select(CharacterSelectors.NAMEDAY).text()
    }

    val portrait = async {
        document.select(CharacterSelectors.PORTRAIT)
            .attr(CharacterSelectors.PORTRAIT_ATTR)
    }

    val pvpTeam = async {
        val pvpTeamName = async {
            document.select(CharacterSelectors.PVP_TEAM).text()
        }

        val pvpTeamId = async {
            document.select(CharacterSelectors.PVP_TEAM)
                .attr(CharacterSelectors.PVP_TEAM_ID_ATTR).toInt()
        }

        val pvpTeamIconLayers = async {
            val bottom = async {
                document.select(CharacterSelectors.PVP_TEAM_BOTTOM_ICON_LAYER)
                    .attr(CharacterSelectors.PVP_TEAM_ICON_LAYER_ATTR)
            }

            val middle = async {
                document.select(CharacterSelectors.PVP_TEAM_MIDDLE_ICON_LAYER)
                    .attr(CharacterSelectors.PVP_TEAM_ICON_LAYER_ATTR)
            }

            val top = async {
                document.select(CharacterSelectors.PVP_TEAM_TOP_ICON_LAYER)
                    .attr(CharacterSelectors.PVP_TEAM_ICON_LAYER_ATTR)
            }

            IconLayers(bottom.await(), middle.await(), top.await())
        }

        Guild(pvpTeamName.await(), pvpTeamId.await(), pvpTeamIconLayers.await())
    }

    val raceClanGender = async {
        document.select(CharacterSelectors.RACE_CLAN_GENDER).text()
    }

    val race = async {
        Race.valueOf(raceClanGender.await())
    }

    val clan = async {
        Clan.valueOf(raceClanGender.await())
    }

    val gender = async {
        Gender.valueOf(raceClanGender.await())
    }

    val world = async {
        World.valueOf(document.select(CharacterSelectors.WORLD).text())
    }

    val dataCenter = async {
        DataCenter.valueOf(document.select(CharacterSelectors.WORLD).text())
    }

    val region = async {
        Region.valueOf(document.select(CharacterSelectors.WORLD).text())
    }

    val title = async {
        document.select(CharacterSelectors.TITLE).text()
    }

    val town = async {
        Town.valueOf(document.select(CharacterSelectors.TOWN).text())
    }

    Character(
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
        world.await(),
        dataCenter.await(),
        region.await(),
        title.await(),
        town.await(),
    )
}
