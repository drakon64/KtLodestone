package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.java.Java
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
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
        header(
            HttpHeaders.UserAgent, if (! mobileUserAgent) {
                userAgentDesktop
            } else {
                userAgentMobile
            }
        )
    }

    return@coroutineScope when (request.status.value) {
        200 -> Jsoup.parse(request.body() as String)
        404 -> throw CharacterNotFoundException("A character with ID `${id}` could not be found on The Lodestone.")
        else -> throw LodestoneException("The Lodestone returned an unknown error.")
    }
}
