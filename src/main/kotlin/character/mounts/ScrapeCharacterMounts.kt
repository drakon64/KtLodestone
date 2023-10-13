package cloud.drakon.ktlodestone.character.mounts

import cloud.drakon.ktlodestone.selectors.character.mounts.MountsSelectors
import org.jsoup.Jsoup

internal fun scrapeCharacterMounts(response: String) = Jsoup.parse(response)
    .select(MountsSelectors.ROOT).let {
        buildList {
            it.forEach {
                add(it.select(MountsSelectors.NAME).text())
            }
        }
    }
