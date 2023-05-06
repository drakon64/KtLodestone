package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.profile.minions.Minion
import cloud.drakon.ktlodestone.profile.minions.ProfileMinions
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

object Minions {
    private val lodestoneCssSelectors = Json.parseToJsonElement(
        this::class.java.classLoader.getResource("lodestone-css-selectors/profile/minion.json") !!
            .readText()
    )

    suspend fun getMinions(id: Int) = coroutineScope {
        val character = getLodestoneProfile(id, "minion", mobileUserAgent = true)

        val minions = async {
            getMinionList(character)
        }
        val total = async {
            getTotalMinions(character)
        }

        return@coroutineScope ProfileMinions(minions.await(), total.await())
    }

    private suspend fun getMinionList(character: Document) = coroutineScope {
        val minions = mutableListOf<Minion>()

        for (i in character.select(lodestoneCssSelectors.jsonObject["MINIONS"] !!.jsonObject["ROOT"] !!.jsonObject["selector"] !!.jsonPrimitive.content)) {
            minions.add(getMinion(i))
        }

        return@coroutineScope minions.toList()
    }

    private suspend fun getMinion(minion: Element) = coroutineScope {
        val name = async {
            minion.select(lodestoneCssSelectors.jsonObject["MINIONS"] !!.jsonObject["NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .text()
        }

        val icon = async {
            minion.select(lodestoneCssSelectors.jsonObject["MINIONS"] !!.jsonObject["ICON"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .attr("src")
        }

        return@coroutineScope Minion(name.await(), icon.await())
    }

    private suspend fun getTotalMinions(character: Document) = coroutineScope {
        return@coroutineScope character.select(lodestoneCssSelectors.jsonObject["TOTAL"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
            .first() !!
            .text()
            .toShort()
    }
}
