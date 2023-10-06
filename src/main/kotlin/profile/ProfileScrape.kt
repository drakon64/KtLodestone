package cloud.drakon.ktlodestone.profile

import cloud.drakon.ktlodestone.KtLodestone.ktorClient
import cloud.drakon.ktlodestone.KtLodestone.userAgentDesktop
import cloud.drakon.ktlodestone.KtLodestone.userAgentMobile
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

internal object ProfileScrape {
    suspend fun getLodestoneProfile(
        id: Int,
        endpoint: String? = null,
        mobileUserAgent: Boolean = false,
    ): Document = ktorClient.get(
        if (endpoint == null) {
            "https://eu.finalfantasyxiv.com/lodestone/character/${id}/"
        } else {
            "https://eu.finalfantasyxiv.com/lodestone/character/${id}/${endpoint}"
        }
    ) {
        header(
            HttpHeaders.UserAgent, if (!mobileUserAgent) {
                userAgentDesktop
            } else {
                userAgentMobile
            }
        )
    }.let {
        when (it.status.value) {
            200 -> Jsoup.parse(it.body() as String)
            404 -> throw CharacterNotFoundException("A character with ID `${id}` could not be found on The Lodestone.")
            else -> throw LodestoneException()
        }
    }

    suspend fun getLodestoneProfilePaginated(
        endpoint: String,
    ): Document = ktorClient.get(endpoint) {
        header(HttpHeaders.UserAgent, userAgentDesktop)
    }.let {
        when (it.status.value) {
            200 -> Jsoup.parse(it.body() as String)
            else -> throw LodestoneException()
        }
    }
}
