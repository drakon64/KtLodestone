package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.Character
import cloud.drakon.ktlodestone.profile.exception.CharacterNotFoundException
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

    private val activeClassJobLevelRegex = """\d+""".toRegex()

    /**
     * Gets a character from The Lodestone
     * @param id The Lodestone character ID
     * @throws CharacterNotFoundException The character could not be found
     * @throws LodestoneException An error was returned by The Lodestone
     */
    suspend fun getCharacter(id: Int): Character = coroutineScope {
        val request =
            ktorClient.get("https://eu.finalfantasyxiv.com/lodestone/character/${id}/")
        val character = when (request.status.value) {
            200  -> Jsoup.parse(request.body() as String)
            404  -> throw CharacterNotFoundException("Character not found.")
            else -> throw LodestoneException("Unknown error.")
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
            character.select("p.character-block__name:nth-child(4)").first() !!.text()
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
                .text()
        }

        val server = async {
            character.select("p.frame__chara__world").first() !!.text()
        }

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
            title.await(),
            town.await()
        )
    }
}
