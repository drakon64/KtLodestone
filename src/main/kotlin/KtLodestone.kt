@file:JvmName("KtUniversalis") @file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.AchievementsScrape
import cloud.drakon.ktlodestone.profile.AttributesScrape
import cloud.drakon.ktlodestone.profile.CharacterScrape
import cloud.drakon.ktlodestone.profile.ClassJobScrape
import cloud.drakon.ktlodestone.profile.GearSetScrape
import cloud.drakon.ktlodestone.profile.MinionsScrape
import cloud.drakon.ktlodestone.profile.MountsScrape
import cloud.drakon.ktlodestone.search.CharacterSearch
import cloud.drakon.ktlodestone.search.World
import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.future
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private val meta = Json.parseToJsonElement(
    object {}::class.java.classLoader.getResource("lodestone-css-selectors/meta.json") !!
        .readText()
)

internal val ktorClient = HttpClient(Java)

internal val userAgentDesktop =
    meta.jsonObject["userAgentDesktop"] !!.jsonPrimitive.content
internal val userAgentMobile =
    meta.jsonObject["userAgentMobile"] !!.jsonPrimitive.content

/**
 * Gets the achievements of a character from The Lodestone. This is equivalent to what is returned by The Lodestone's `/achievement` endpoint for a character.
 * @param id The Lodestone character ID.
 * @param pages The number of pages to get achievements from
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun getAchievements(id: Int, pages: Byte? = null) =
    coroutineScope { AchievementsScrape.getAchievements(id, pages) }

/**
 * Gets the achievements of a character from The Lodestone. This is equivalent to what is returned by The Lodestone's `/achievement` endpoint for a character. For use outside of Kotlin coroutines.
 * @param id The Lodestone character ID.
 * * @param pages The number of pages to get achievements from
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
fun getAchievementsAsync(id: Int, pages: Byte? = null) =
    GlobalScope.future { AchievementsScrape.getAchievements(id, pages) }

/**
 * Gets the attributes of a character from The Lodestone. This is equivalent to what is returned by The Lodestone's `#profile` endpoint for a character.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun getAttributes(id: Int) =
    coroutineScope { AttributesScrape.getAttributes(id) }

/**
 * Gets the attributes of a character from The Lodestone. This is equivalent to what is returned by The Lodestone's `#profile` endpoint for a character. For use outside of Kotlin coroutines.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
fun getAttributesAsync(id: Int) =
    GlobalScope.future { AttributesScrape.getAttributes(id) }

/**
 * Gets a character's profile from The Lodestone. This is equivalent to what is returned by The Lodestone's `#profile` endpoint for a character.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun getCharacter(id: Int) = coroutineScope { CharacterScrape.getCharacter(id) }

/**
 * Gets a character's profile from The Lodestone. This is equivalent to what is returned by The Lodestone's `#profile` endpoint for a character. For use outside of Kotlin coroutines.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
fun getCharacterAsync(id: Int) = GlobalScope.future { CharacterScrape.getCharacter(id) }

/**
 * Gets a characters class/job stats from The Lodestone. This is equivalent to what is returned by The Lodestone's `/class_job` endpoint for a character.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun getClassJob(id: Int) = coroutineScope { ClassJobScrape.getClassJob(id) }

/**
 * Gets a characters class/job stats from The Lodestone. This is equivalent to what is returned by The Lodestone's `/class_job` endpoint for a character. For use outside of Kotlin coroutines.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
fun getClassJobAsync(id: Int) = GlobalScope.future { ClassJobScrape.getClassJob(id) }

/**
 * Gets a characters equipped gear set from The Lodestone.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun getGearSet(id: Int) = coroutineScope { GearSetScrape.getGearSet(id) }

/**
 * Gets a characters equipped gear set from The Lodestone. For use outside of Kotlin coroutines.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
fun getGearSetAsync(id: Int) = GlobalScope.future { GearSetScrape.getGearSet(id) }

/**
 * Gets the minions that a character on The Lodestone has acquired. This is equivalent to what is returned by The Lodestone's `/minion` endpoint for a character.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun getMinions(id: Int) = coroutineScope { MinionsScrape.getMinions(id) }

/**
 * Gets the minions that a character on The Lodestone has acquired. This is equivalent to what is returned by The Lodestone's `/minion` endpoint for a character. For use outside of Kotlin coroutines.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
fun getMinionsAsync(id: Int) = GlobalScope.future { MinionsScrape.getMinions(id) }

/**
 * Gets the mounts that a character on The Lodestone has acquired. This is equivalent to what is returned by The Lodestone's `/mount` endpoint for a character.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun getMounts(id: Int) = coroutineScope { MountsScrape.getMounts(id) }

/**
 * Gets the mounts that a character on The Lodestone has acquired. This is equivalent to what is returned by The Lodestone's `/mount` endpoint for a character. For use outside of Kotlin coroutines.
 * @param id The Lodestone character ID.
 * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
fun getMountsAsync(id: Int) = GlobalScope.future { MountsScrape.getMounts(id) }

/**
 * Searches for a character on The Lodestone.
 * @param name The Lodestone character name.
 * @param world The Lodestone character world.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun searchCharacter(name: String, world: World) = coroutineScope {
    return@coroutineScope CharacterSearch.characterSearch(name, world)
}

/**
 * Searches for a character on The Lodestone. For use outside of Kotlin coroutines.
 * @param name The Lodestone character name.
 * @param world The Lodestone character world.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
fun searchCharacterAsync(name: String, world: World) = GlobalScope.future {
    return@future CharacterSearch.characterSearch(name, world)
}
