@file:JvmName("KtLodestone")
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.character.scrapeCharacter
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import kotlin.jvm.Throws
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

suspend fun getLodestoneCharacter(id: Int) = ktorClient.get("character/$id/").let {
    when (it.status.value) {
        200 -> scrapeCharacter(it.body())
        404 -> throw LodestoneNotFoundException("Character", id)
        else -> throw LodestoneException()
    }
}

@JvmName("getLodestoneCharacter")
@Throws(LodestoneNotFoundException::class, LodestoneException::class)
fun getLodestoneCharacterAsync(id: Int) = GlobalScope.future {
    getLodestoneCharacter(id)
}
