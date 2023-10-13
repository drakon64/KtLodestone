package cloud.drakon.ktlodestone.character.minions

import cloud.drakon.ktlodestone.selectors.character.minions.MinionsSelectors
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

internal suspend fun scrapeCharacterMinions(response: String) = Jsoup.parse(response).let {
    it.select(MinionsSelectors.ROOT).let {
        buildList {
            it.forEach {
                add(it.select(MinionsSelectors.NAME).text())
            }
        }
    }
}
