package cloud.drakon.ktlodestone.profile

import cloud.drakon.ktlodestone.profile.mounts.Mount
import cloud.drakon.ktlodestone.profile.mounts.ProfileMounts
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

internal object MountsScrape {
    private val lodestoneCssSelectors = Json.parseToJsonElement(
        this::class.java.classLoader.getResource("lodestone-css-selectors/profile/mount.json") !!
            .readText()
    )

    suspend fun getMounts(id: Int) = coroutineScope {
        val character = ProfileScrape.getLodestoneProfile(
            id, "mount", mobileUserAgent = true
        )

        val mounts = async {
            getMountList(character)
        }
        val total = async {
            getTotalMounts(character)
        }

        return@coroutineScope ProfileMounts(mounts.await(), total.await())
    }

    private suspend fun getMountList(character: Document): Map<String, Mount> {
        val mounts = mutableMapOf<String, Mount>()

        character.select(lodestoneCssSelectors.jsonObject["MOUNTS"] !!.jsonObject["ROOT"] !!.jsonObject["selector"] !!.jsonPrimitive.content).forEach {
            val mount = getMount(it)

            mounts[mount.name] = mount
        }

        return mounts.toMap()
    }

    private suspend fun getMount(mount: Element) = coroutineScope {
        val name = async {
            mount.select(lodestoneCssSelectors.jsonObject["MOUNTS"] !!.jsonObject["NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .text()
        }

        val icon = async {
            mount.select(lodestoneCssSelectors.jsonObject["MOUNTS"] !!.jsonObject["ICON"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                .first() !!
                .attr("data-original")
        }

        return@coroutineScope Mount(name.await(), icon.await())
    }

    private fun getTotalMounts(character: Document) = character.select(
        lodestoneCssSelectors.jsonObject["TOTAL"] !!.jsonObject["selector"] !!.jsonPrimitive.content
    ).first() !!.text().toShort()
}
