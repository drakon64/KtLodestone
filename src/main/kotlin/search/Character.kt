package cloud.drakon.ktlodestone.search

import cloud.drakon.ktlodestone.KtLodestone
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.search.character.SearchCharacter
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

internal object Character {
    private val lodestoneCssSelectors = Json.parseToJsonElement(
        this::class.java.classLoader.getResource("lodestone-css-selectors/search/character.json") !!
            .readText()
    )

    suspend fun characterSearch(name: String, world: World) = coroutineScope {
        val request =
            KtLodestone.ktorClient.get("https://eu.finalfantasyxiv.com/lodestone/character/") {
                header(
                    HttpHeaders.UserAgent, KtLodestone.userAgentDesktop
                )

                parameter("q", name)
                parameter("worldname", world.name)
            }

        return@coroutineScope when (request.status.value) {
            200 -> getCharacterSearchResults(Jsoup.parse(request.body() as String))
            404 -> throw CharacterNotFoundException("A character named `$name` on the world `${world.name}` could not be found on The Lodestone.")
            else -> throw LodestoneException()
        }
    }

    private suspend fun getCharacterSearchResults(results: Document) = coroutineScope {
        val search =
            results.select(lodestoneCssSelectors.jsonObject["ROOT"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .select(lodestoneCssSelectors.jsonObject["ENTRY"] !!.jsonObject["ROOT"] !!.jsonObject["selector"] !!.jsonPrimitive.content)

        val characters = mutableListOf<SearchCharacter>()

        for (character in search) {
            characters.add(getCharacter(character))
        }

        return@coroutineScope if (characters.isNotEmpty()) {
            characters.toList()
        } else {
            null
        }
    }

    private val idRegex = """\d+""".toRegex()

    private val grandCompanyNameRegex = """\w+""".toRegex()
    private val grandCompanyRankRegex = """(?<=\/ ).*""".toRegex()

    private val serverRegex = """\S+""".toRegex()
    private val dcRegex = """(?<=\[)\w+(?=\])""".toRegex()

    private suspend fun getCharacter(character: Element) = coroutineScope {
        val lodestoneCss = lodestoneCssSelectors.jsonObject["ENTRY"] !!

        val avatar = async {
            val css = lodestoneCss.jsonObject["AVATAR"] !!
            character.select(css.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .attr(css.jsonObject["attribute"] !!.jsonPrimitive.content)
        }

        val id = async {
            val css = lodestoneCss.jsonObject["ID"] !!
            idRegex.find(
                character.select(css.jsonObject["selector"] !!.jsonPrimitive.content)
                    .first() !!
                    .attr(css.jsonObject["attribute"] !!.jsonPrimitive.content)
            ) !!.value.toInt()
        }

        val language = async {
            val css = lodestoneCss.jsonObject["LANG"] !!
            character.select(css.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .text()
        }

        val name = async {
            val css = lodestoneCss.jsonObject["NAME"] !!
            character.select(css.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .text()
        }

        val gc = async {
            val css = lodestoneCss.jsonObject["RANK"] !!
            character.select(css.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .attr(css.jsonObject["attribute"] !!.jsonPrimitive.content)
        }
        val gcName = async {
            grandCompanyNameRegex.find(gc.await()) !!.value
        }
        val gcRank = async {
            grandCompanyRankRegex.find(gc.await()) !!.value
        }

        val gcRankIcon = async {
            val css = lodestoneCss.jsonObject["RANK_ICON"] !!
            character.select(css.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .attr(css.jsonObject["attribute"] !!.jsonPrimitive.content)
        }

        val serverDc = async {
            val css = lodestoneCss.jsonObject["SERVER"] !!
            character.select(css.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .text()
        }
        val server = async {
            serverRegex.find(serverDc.await()) !!.value
        }
        val dc = async {
            dcRegex.find(serverDc.await()) !!.value
        }

        return@coroutineScope SearchCharacter(
            avatar.await(),
            id.await(),
            language.await(),
            name.await(),
            gcName.await(),
            gcRank.await(),
            gcRankIcon.await(),
            server.await(),
            dc.await()
        )
    }
}
