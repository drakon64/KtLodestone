package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.profile.Character
import cloud.drakon.ktlodestone.profile.GuardianDeity
import cloud.drakon.ktlodestone.profile.Town
import cloud.drakon.ktlodestone.profile.freecompany.FreeCompany
import cloud.drakon.ktlodestone.profile.freecompany.FreeCompanyIconLayers
import cloud.drakon.ktlodestone.profile.pvpteam.PvpTeam
import cloud.drakon.ktlodestone.profile.pvpteam.PvpTeamIconLayers
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.java.Java
import io.ktor.client.request.get
import org.jsoup.Jsoup

object KtLodestone {
    private val ktorClient = HttpClient(Java)

    suspend fun getCharacter(id: Int): Character {
        val character = Jsoup.parse(
            ktorClient.get("https://eu.finalfantasyxiv.com/lodestone/character/${id}/")
                .body() as String
        )

        return Character(
            activeClassJob = character.select(".character__class_icon > img:nth-child(1)")
                .first() !!
                .attr("src"),
            activeClassJobLevel = character.select(".character__class__data > p:nth-child(1)")
                .first() !!
                .text()
                .toByte(),
            avatar = character.select(".frame__chara__face > img:nth-child(1)")
                .first() !!
                .attr("src"),
            bio = character.select(".character__selfintroduction").first() !!.text(),
            freeCompany = FreeCompany(
                id = character.select(".character__freecompany__name > h4:nth-child(2) > a:nth-child(1)")
                    .first() !!
                    .attr("href"), iconLayers = FreeCompanyIconLayers(
                    bottom = character.select("div.character__freecompany__crest > div > img:nth-child(1)")
                        .first() !!
                        .attr("src"),
                    middle = character.select("div.character__freecompany__crest > div > img:nth-child(1)")
                        .first() !!
                        .attr("src"),
                    top = character.select("div.character__freecompany__crest > div > img:nth-child(1)")
                        .first() !!
                        .attr("src")
                )
            ),
            grandCompany = character.select("div.character-block:nth-child(4) > div:nth-child(2) > p:nth-child(2)")
                .first() !!
                .text(),
            guardianDeity = GuardianDeity(
                name = character.select("p.character-block__name:nth-child(4)")
                    .first() !!
                    .text(),
                icon = character.select("#character > div.character__content.selected > div.character__profile.clearfix > div.character__profile__data > div:nth-child(1) > div > div:nth-child(2) > img")
                    .first() !!
                    .attr("src")
            ),
            name = character.select("div.frame__chara__box:nth-child(2) > .frame__chara__name")
                .first() !!
                .text(),
            nameday = character.select(".character-block__birth").first() !!.text(),
            portrait = character.select(".js__image_popup > img:nth-child(1)")
                .first() !!
                .attr("src"),
            pvpTeam = PvpTeam(
                name = character.select(".character__pvpteam__name > h4:nth-child(2) > a:nth-child(1)")
                    .first() !!
                    .attr("href"), iconLayers = PvpTeamIconLayers(
                    bottom = character.select(".character__pvpteam__crest__image img:nth-child(1)")
                        .first() !!
                        .attr("src"),
                    middle = character.select(".character__pvpteam__crest__image img:nth-child(2)")
                        .first() !!
                        .attr("src"),
                    top = character.select(".character__pvpteam__crest__image img:nth-child(3)")
                        .first() !!
                        .attr("src")
                )
            ),
            raceClanGender = character.select("div.character-block:nth-child(1) > div:nth-child(2) > p:nth-child(2)")
                .first() !!
                .text(),
            server = character.select("p.frame__chara__world").first() !!.text(),
            title = character.select(".frame__chara__title").first() !!.text(),
            town = Town(
                name = character.select("div.character-block:nth-child(3) > div:nth-child(2) > p:nth-child(2)")
                    .first() !!
                    .text(),
                icon = character.select("#character > div.character__content.selected > div.character__profile.clearfix > div.character__profile__data > div:nth-child(1) > div > div:nth-child(3) > img")
                    .first() !!
                    .attr("src")
            )
        )
    }
}
