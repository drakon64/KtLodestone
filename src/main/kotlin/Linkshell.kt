@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import cloud.drakon.ktlodestone.linkshell.ScrapeLinkshell
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

/**
 * Returns a Linkshell with ID [id] from The Lodestone. This matches what is found on The Lodestone's `/linkshell` endpoint (or `/crossworld_linkshell` for Cross-world Linkshells).

 * @throws LodestoneNotFoundException Thrown when the Linkshell isn't found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun getLodestoneLinkshell(
    id: String,
    crossWorld: Boolean = false,
) = ktorClient.get(
    if (crossWorld) {
        "crossworld_linkshell"
    } else {
        "linkshell"
    } + "/$id/"
).let {
    when (it.status.value) {
        200 -> ScrapeLinkshell().scrapeLinkshell(it.body())
        404 -> throw LodestoneNotFoundException("Linkshell", id)
        else -> throw LodestoneException()
    }
}

/**
 * Returns a Linkshell with ID [id] from The Lodestone. This matches what is found on The Lodestone's `/linkshell` endpoint (or `/crossworld_linkshell` for Cross-world Linkshells).

 * @throws LodestoneNotFoundException Thrown when the Linkshell isn't found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
@JvmName("getLodestoneLinkshell")
@Throws(LodestoneNotFoundException::class, LodestoneException::class)
fun getLodestoneLinkshellAsync(id: String, crossWorld: Boolean = false) = GlobalScope.future {
    getLodestoneLinkshell(id, crossWorld)
}
