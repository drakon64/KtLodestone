@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.character.classjob.scrapeCharacterClassJob
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

/**
 * Returns a characters unlocked classes/jobs and their level from The Lodestone. This matches what is found on The Lodestone's `/character/class_job` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun getLodestoneCharacterClassJob(id: Int) = ktorClient.get("character/$id/class_job/").let {
    when (it.status.value) {
        200 -> scrapeCharacterClassJob(it.body())
        404 -> throw LodestoneNotFoundException("Character", id)
        else -> throw LodestoneException()
    }
}

/**
 * Returns a characters unlocked classes/jobs and their level from The Lodestone. This matches what is found on The Lodestone's `/character/class_job` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
@JvmName("getLodestoneCharacterClassJob")
@Throws(LodestoneNotFoundException::class, LodestoneException::class)
fun getLodestoneCharacterClassJobAsync(id: Int) = GlobalScope.future {
    getLodestoneCharacterClassJob(id)
}
