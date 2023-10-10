@file:JvmName("KtLodestone")
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.character.profile.CharacterProfile
import cloud.drakon.ktlodestone.character.profile.scrapeCharacter
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

private val ktorClient = HttpClient {
    defaultRequest {
        url("https://eu.finalfantasyxiv.com/lodestone/")
    }

    install(UserAgent) {
        agent = "curl/7.73.0"
    }
}

/**
 * Returns [CharacterProfile] with ID [id] from *The Lodestone*. This matches what is found on *The Lodestone*'s `/character` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on *The Lodestone*.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
suspend fun getLodestoneCharacterProfile(id: Int) = ktorClient.get("character/$id/").let {
    when (it.status.value) {
        200 -> scrapeCharacter(it.body())
        404 -> throw LodestoneNotFoundException("Character", id)
        else -> throw LodestoneException()
    }
}

/**
 * Returns [CharacterProfile] with ID [id] from *The Lodestone*. This matches what is found on *The Lodestone*'s `/character` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on *The Lodestone*.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
@JvmName("getLodestoneCharacter")
@Throws(LodestoneNotFoundException::class, LodestoneException::class)
fun getLodestoneCharacterProfileAsync(id: Int) = GlobalScope.future {
    getLodestoneCharacterProfile(id)
}
