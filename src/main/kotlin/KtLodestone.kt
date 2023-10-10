@file:JvmName("KtLodestone")
@file:JvmMultifileClass

package cloud.drakon.ktlodestone

import io.ktor.client.HttpClient
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.defaultRequest
import kotlinx.coroutines.DelicateCoroutinesApi

internal val ktorClient = HttpClient {
    defaultRequest {
        url("https://eu.finalfantasyxiv.com/lodestone/")
    }

    install(UserAgent) {
        agent = "curl/7.73.0"
    }
}
