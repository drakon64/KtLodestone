@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.character.ClassJob
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.profile.Clan
import cloud.drakon.ktlodestone.character.profile.Race
import cloud.drakon.ktlodestone.character.search.CharacterSearch
import cloud.drakon.ktlodestone.character.search.Language
import cloud.drakon.ktlodestone.exception.InvalidParameterException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

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
 * @throws InvalidParameterException Thrown when [pages] is a value less than 1 or greater than 20.
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
) = if (pages in 1..20) CharacterSearch().scrapeCharacterSearch(
    name,
    world,
    dataCenter,
    classJob,
    race,
    clan,
    grandCompanies,
    languages,
    pages,
) else throw InvalidParameterException("`pages` must be at least 1 and less than or equal to 20.")

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
