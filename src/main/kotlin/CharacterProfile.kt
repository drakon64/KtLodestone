@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.character.profile.CharacterProfile
import cloud.drakon.ktlodestone.character.profile.scrapeCharacterProfile
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

/**
 * Returns a [CharacterProfile] with ID [id] from The Lodestone. This matches what is found on The Lodestone's `/character` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun getLodestoneCharacterProfile(id: Int) = ktorClient.get("character/$id/").let {
    when (it.status.value) {
        200 -> scrapeCharacterProfile(it.body())
        404 -> throw LodestoneNotFoundException("Character", id)
        else -> throw LodestoneException()
    }
}

/**
 * Returns a [CharacterProfile] with ID [id] from The Lodestone. This matches what is found on The Lodestone's `/character` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
@JvmName("getLodestoneCharacter")
@Throws(LodestoneNotFoundException::class, LodestoneException::class)
fun getLodestoneCharacterProfileAsync(id: Int) = GlobalScope.future {
    getLodestoneCharacterProfile(id)
}
