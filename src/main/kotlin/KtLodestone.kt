package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.java.Java
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.Jsoup

private val meta = Json.parseToJsonElement(
    object {}::class.java.classLoader.getResource("lodestone-css-selectors/meta.json") !!
        .readText()
)

private val userAgentDesktop =
    meta.jsonObject["userAgentDesktop"] !!.jsonPrimitive.content
private val userAgentMobile =
    meta.jsonObject["userAgentMobile"] !!.jsonPrimitive.content

private val ktorClient = HttpClient(Java)

internal suspend fun getLodestoneProfile(
    id: Int,
    endpoint: String? = null,
    mobileUserAgent: Boolean = false,
) = coroutineScope {
    val url = if (endpoint == null) {
        "https://eu.finalfantasyxiv.com/lodestone/character/${id}/"
    } else {
        "https://eu.finalfantasyxiv.com/lodestone/character/${id}/${endpoint}"
    }

    val request = ktorClient.get(url) {
        headers {
            if (! mobileUserAgent) {
                append(HttpHeaders.UserAgent, userAgentDesktop)
            } else {
                append(HttpHeaders.UserAgent, userAgentMobile)
            }
        }
    }

    return@coroutineScope when (request.status.value) {
        200 -> Jsoup.parse(request.body() as String)
        404 -> throw CharacterNotFoundException("Thrown when a character could not be found on The Lodestone.")
        else -> throw LodestoneException("Thrown when The Lodestone returns an unknown error.")
    }
}
