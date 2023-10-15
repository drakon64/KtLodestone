@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import cloud.drakon.ktlodestone.selectors.character.minions.MinionsSelectors
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.userAgent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.jsoup.Jsoup

/**
 * Returns a list of minions unlocked by a character with ID [id] from *The Lodestone*. This matches what is found on *The Lodestone*'s `/character/minion` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on *The Lodestone*.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
suspend fun getLodestoneCharacterMinions(id: Int): List<String> = ktorClient.get("character/$id/minion/") {
    userAgent("Mozilla/5.0 (iPhone; CPU OS 10_15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.1 Mobile/14E304 Safari/605.1.15")
}.let {
    when (it.status.value) {
        200 -> Jsoup.parse(it.body() as String).select(MinionsSelectors.ROOT).let {
            buildList {
                it.forEach {
                    add(it.select(MinionsSelectors.NAME).text())
                }
            }
        }

        404 -> throw LodestoneNotFoundException("Character", id)
        else -> throw LodestoneException()
    }
}

/**
 * Returns a list of minions unlocked by a character with ID [id] from *The Lodestone*. This matches what is found on *The Lodestone*'s `/character/minion` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on *The Lodestone*.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
@JvmName("getLodestoneCharacterMinions")
@Throws(LodestoneNotFoundException::class, LodestoneException::class)
fun getLodestoneCharacterMinionsAsync(id: Int) = GlobalScope.future {
    getLodestoneCharacterMinions(id)
}
