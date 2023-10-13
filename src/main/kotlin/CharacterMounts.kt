@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.character.mounts.scrapeCharacterMounts
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.userAgent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

/**
 * Returns a list of mounts unlocked by a character with ID [id] from *The Lodestone*. This matches what is found on *The Lodestone*'s `/character/mount` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on *The Lodestone*.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
suspend fun getLodestoneCharacterMounts(id: Int) = ktorClient.get("character/$id/mount/") {
    userAgent("Mozilla/5.0 (iPhone; CPU OS 10_15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.1 Mobile/14E304 Safari/605.1.15")
}.let {
    when (it.status.value) {
        200 -> scrapeCharacterMounts(it.body())
        404 -> throw LodestoneNotFoundException("Character", id)
        else -> throw LodestoneException()
    }
}

/**
 * Returns a list of mounts unlocked by a character with ID [id] from *The Lodestone*. This matches what is found on *The Lodestone*'s `/character/mount` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on *The Lodestone*.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
@JvmName("getLodestoneCharacterMounts")
@Throws(LodestoneNotFoundException::class, LodestoneException::class)
fun getLodestoneCharacterMountsAsync(id: Int) = GlobalScope.future {
    getLodestoneCharacterMounts(id)
}
