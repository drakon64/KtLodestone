package cloud.drakon.ktlodestone.character.minions

import cloud.drakon.ktlodestone.selectors.character.minions.MinionsSelectors
import org.jsoup.Jsoup

internal fun scrapeCharacterMinions(response: String) = Jsoup.parse(response)
    .select(MinionsSelectors.ROOT).let {
        buildList {
            it.forEach {
                add(it.select(MinionsSelectors.NAME).text())
            }
        }
    }
