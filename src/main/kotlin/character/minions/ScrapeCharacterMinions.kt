package cloud.drakon.ktlodestone.character.minions

import cloud.drakon.ktlodestone.selectors.character.minions.MinionsSelectors
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

internal suspend fun scrapeCharacterMinions(response: String) = coroutineScope {
    Jsoup.parse(response).let {
        val minions = async {
            it.select(MinionsSelectors.ROOT).let {
                buildList {
                    it.forEach {
                        add(it.select(MinionsSelectors.NAME).text())
                    }
                }
            }
        }

        val total = async {
            it.select(MinionsSelectors.TOTAL).text().toShort()
        }

        CharacterMinions(minions.await(), total.await())
    }
}
