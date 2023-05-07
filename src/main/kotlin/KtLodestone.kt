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
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.Jsoup

object KtLodestone {
    /**
     * Gets the attributes of a character from The Lodestone. This is equivalent to what is returned by The Lodestone's `/attributes` endpoint for a character.
     * @param id The Lodestone character ID.
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    suspend fun getAttributes(id: Int) = coroutineScope { Attributes.getAttributes(id) }

    /**
     * Gets the attributes of a character from The Lodestone. This is equivalent to what is returned by The Lodestone's `/attributes` endpoint for a character. For use outside of Kotlin coroutines.
     * @param id The Lodestone character ID.
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    @JvmStatic fun getAttributesBlocking(id: Int) =
        runBlocking { Attributes.getAttributes(id) }

    /**
     * Gets a character's profile from The Lodestone.
     * @param id The Lodestone character ID.
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    suspend fun getCharacter(id: Int) = coroutineScope { Character.getCharacter(id) }

    /**
     * Gets a character's profile from The Lodestone. For use outside of Kotlin coroutines.
     * @param id The Lodestone character ID.
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    @JvmStatic fun getCharacterBlocking(id: Int) =
        runBlocking { Character.getCharacter(id) }

    /**
     * Gets a characters class/job stats from The Lodestone. This is equivalent to what is returned by The Lodestone's `/class_job` endpoint for a character.
     * @param id The Lodestone character ID.
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    suspend fun getClassJob(id: Int) = coroutineScope { ClassJob.getClassJob(id) }

    /**
     * Gets a characters class/job stats from The Lodestone. This is equivalent to what is returned by The Lodestone's `/class_job` endpoint for a character. For use outside of Kotlin coroutines.
     * @param id The Lodestone character ID.
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    @JvmStatic fun getClassJobBlocking(id: Int) =
        runBlocking { ClassJob.getClassJob(id) }

    /**
     * Gets a characters equipped gear set from The Lodestone.
     * @param id The Lodestone character ID.
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    suspend fun getGearSet(id: Int) = coroutineScope { GearSet.getGearSet(id) }

    /**
     * Gets a characters equipped gear set from The Lodestone. For use outside of Kotlin coroutines.
     * @param id The Lodestone character ID.
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    @JvmStatic fun getGearSetBlocking(id: Int) = runBlocking { GearSet.getGearSet(id) }

    /**
     * Gets the minions that a character on The Lodestone has acquired. This is equivalent to what is returned by The Lodestone's `/minions` endpoint for a character.
     * @param id The Lodestone character ID.
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    suspend fun getMinions(id: Int) = coroutineScope { Minions.getMinions(id) }

    /**
     * Gets the minions that a character on The Lodestone has acquired. This is equivalent to what is returned by The Lodestone's `/minions` endpoint for a character. For use outside of Kotlin coroutines.
     * @param id The Lodestone character ID.
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    @JvmStatic fun getMinionsBlocking(id: Int) = runBlocking { Minions.getMinions(id) }

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
}
