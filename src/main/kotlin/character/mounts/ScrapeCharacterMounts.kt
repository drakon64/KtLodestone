package cloud.drakon.ktlodestone.character.mounts

import cloud.drakon.ktlodestone.selectors.character.mounts.MountsSelectors
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

internal suspend fun scrapeCharacterMounts(response: String) = Jsoup.parse(response).let {
    it.select(MountsSelectors.ROOT).let {
        buildList {
            it.forEach {
                add(it.select(MountsSelectors.NAME).text())
            }
        }
    }
}
