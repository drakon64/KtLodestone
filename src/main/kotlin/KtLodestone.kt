@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

internal object KtLodestone {
    private val meta = Json.parseToJsonElement(
        this::class.java.classLoader.getResource("lodestone-css-selectors/meta.json") !!
            .readText()
    )

    val ktorClient = HttpClient(Java)

    val userAgentDesktop = meta.jsonObject["userAgentDesktop"] !!.jsonPrimitive.content
    val userAgentMobile = meta.jsonObject["userAgentMobile"] !!.jsonPrimitive.content
}
