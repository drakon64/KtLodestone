@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.character.ClassJob
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.profile.Clan
import cloud.drakon.ktlodestone.character.profile.Race
import cloud.drakon.ktlodestone.character.search.Language
import cloud.drakon.ktlodestone.character.search.scrapeCharacterSearch
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.security.InvalidParameterException

/**
 * Searches for a character on *The Lodestone*.
 *
 * @param name The name of the character to search for.
 * @param world Search this [World] for characters. Takes priority over [dataCenter].
 * @param dataCenter Search this [DataCenter] for characters.
 * @param classJob Search for characters that have this [ClassJob] active.
 * @param race Search for characters of this [Race].
 * @param clan Search for characters of this [Clan]. Takes priority over [race].
 * @param grandCompanies Search for characters that a member of one of these [GrandCompanyName]. If `null`, all [GrandCompanyName] entries are considered, as are "Non Affiliated" characters. If a [Set] with a `null` element is provided, `null` is treated as "Non Affiliated".
 * @param languages Search for characters with this [Set] of [Language] selected.
 * @param pages The number of pages of characters to return. One page contains twenty characters.
 * @throws InvalidParameterException Thrown when [pages] is a value less than 1.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
suspend fun searchLodestoneCharacter(
    name: String? = null,
    world: World? = null,
    dataCenter: DataCenter? = null,
    classJob: ClassJob? = null,
    race: Race? = null,
    clan: Clan? = null,
    grandCompanies: Set<GrandCompanyName?>? = null,
    languages: Set<Language>? = null,
    pages: Byte = 1,
) = if (pages == 1.toByte()) {
    searchLodestoneCharacterPaginated(
        name,
        world,
        dataCenter,
        classJob,
        race,
        clan,
        grandCompanies,
        languages
    )
} else if (pages > 1.toByte()) {
    buildList {
        for (page in 1..pages) {
            add(
                searchLodestoneCharacterPaginated(
                    name,
                    world,
                    dataCenter,
                    classJob,
                    race,
                    clan,
                    grandCompanies,
                    languages,
                    page
                )
            )
        }
    }.flatten()
} else {
    throw InvalidParameterException("`pages` must be at least 1.")
}

/**
 * Searches for a character on *The Lodestone*.
 *
 * @param name The name of the character to search for.
 * @param world Search this [World] for characters. Takes priority over [dataCenter].
 * @param dataCenter Search this [DataCenter] for characters.
 * @param classJob Search for characters that have this [ClassJob] active.
 * @param race Search for characters of this [Race].
 * @param clan Search for characters of this [Clan]. Takes priority over [race].
 * @param grandCompanies Search for characters that a member of one of these [GrandCompanyName]. If `null`, all [GrandCompanyName] entries are considered, as are "Non Affiliated" characters. If a [Set] with a `null` element is provided, `null` is treated as "Non Affiliated".
 * @param languages Search for characters with this [Set] of [Language] selected.
 * @param pages The number of pages of characters to return. One page contains twenty characters.
 * @throws InvalidParameterException Thrown when [pages] is a value less than 1.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
@JvmName("searchLodestoneCharacter")
@JvmOverloads
@Throws(InvalidParameterException::class, LodestoneException::class)
fun searchLodestoneCharacterAsync(
    name: String? = null,
    world: World? = null,
    dataCenter: DataCenter? = null,
    classJob: ClassJob? = null,
    race: Race? = null,
    clan: Clan? = null,
    grandCompanies: Set<GrandCompanyName?>? = null,
    languages: Set<Language>? = null,
    pages: Byte = 1,
) = GlobalScope.future {
    searchLodestoneCharacter(
        name,
        world,
        dataCenter,
        classJob,
        race,
        clan,
        grandCompanies,
        languages,
        pages,
    )
}

private suspend fun searchLodestoneCharacterPaginated(
    name: String?,
    world: World?,
    dataCenter: DataCenter?,
    classJob: ClassJob?,
    race: Race?,
    clan: Clan?,
    grandCompanies: Set<GrandCompanyName?>?,
    languages: Set<Language>?,
    page: Int? = null,
) = ktorClient.get("character/") {
    url {
        // We've already encoded this parameter
        if (name != null) encodedParameters.append("q", name.replace(" ", "+"))

        // If both [world] and [dataCenter] or just [world] are provided, use [world] as it's more specific, otherwise use [dataCenter]
        if ((world != null && dataCenter != null) || world != null) {
            parameters.append("worldname", world.name)
        } else if (dataCenter != null) {
            parameters.append("worldname", dataCenter.name)
        }

        if (classJob != null) parameters.append("classjob", classJob.name)

        // If both [clan] and [race] or just [clan] are provided, use [clan] as it's more specific, otherwise use [race]
        if ((clan != null && race != null) || clan != null) {
            parameters.append("race_tribe", clan.name)
        } else if (race != null) {
            parameters.append("race_tribe", race.name)
        }

        grandCompanies?.forEach {
            when (it) {
                GrandCompanyName.MAELSTROM -> parameters.append("gcid", "1")
                GrandCompanyName.ORDER_OF_THE_TWIN_ADDER -> parameters.append("gcid", "2")
                GrandCompanyName.IMMORTAL_FLAMES -> parameters.append("gcid", "3")
                null -> parameters.append("gcid", "0")
            }
        }

        languages?.forEach {
            when (it) {
                Language.JAPANESE -> parameters.append("blog_lang", "ja")
                Language.ENGLISH -> parameters.append("blog_lang", "en")
                Language.GERMAN -> parameters.append("blog_lang", "de")
                Language.FRENCH -> parameters.append("blog_lang", "fr")
            }
        }

        if (page != null) parameters.append("page", "$page")
    }
}.let {
    when (it.status.value) {
        200 -> scrapeCharacterSearch(it.body())
        else -> throw LodestoneException()
    }
}
