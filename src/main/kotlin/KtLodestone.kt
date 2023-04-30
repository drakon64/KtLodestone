package cloud.drakon.ktlodestone

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.UserAgent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private val meta = Json.parseToJsonElement(
    object {}::class.java.classLoader.getResource("lodestone-css-selectors/meta.json") !!
        .readText()
)

internal val ktorClient = HttpClient(Java) {
    install(UserAgent) {
        agent = meta.jsonObject["userAgentDesktop"] !!.jsonPrimitive.content
    }
}
