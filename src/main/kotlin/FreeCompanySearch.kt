@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.exception.InvalidParameterException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.freecompany.search.Active
import cloud.drakon.ktlodestone.freecompany.search.Focus
import cloud.drakon.ktlodestone.freecompany.search.FreeCompanySearch
import cloud.drakon.ktlodestone.freecompany.search.Housing
import cloud.drakon.ktlodestone.freecompany.search.Recruitment
import cloud.drakon.ktlodestone.freecompany.search.Seeking
import cloud.drakon.ktlodestone.search.ActiveMembers
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

/**
 * Searches for a Free Company on *The Lodestone*.
 *
 * @param name The name of the Free Company to search for.
 * @param communityFinder Search for Free Company that are recruiting via the Community Finder.
 * @param world Search this [World] for Free Companies. Takes priority over [dataCenter].
 * @param dataCenter Search this [DataCenter] for Free Companies.
 * @param activeMembers Search for Free Companies with this many members.
 * @param focus Search for Free Companies with this focus. If `null`, all focuses are considered, including "Not specified". If a [Set] with a `null` element is provided, `null` is treated as "Not specified".
 * @param seeking Search for Free Companies who are seeking this role. If `null`, all roles are considered, including "Not specified". If a [Set] with a `null` element is provided, `null` is treated as "Not specified".
 * @param active Search for Free Companies that are active during this time.
 * @param recruitment Search for Free Companies with this recruitment state.
 * @param housing Search for Free Companies with this housing state.
 * @param grandCompany Search for Free Companies associated with this Grand Company.
 * @throws InvalidParameterException Thrown when [pages] is a value less than 1 or greater than 20.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
suspend fun searchLodestoneFreeCompany(
    name: String? = null,
    communityFinder: Boolean = false,
    world: World? = null,
    dataCenter: DataCenter? = null,
    activeMembers: ActiveMembers? = null,
    focus: Set<Focus?>? = null,
    seeking: Set<Seeking?>? = null,
    active: Active? = null,
    recruitment: Recruitment? = null,
    housing: Housing? = null,
    grandCompany: Set<GrandCompanyName>? = null,
    pages: Byte = 1,
) = if (pages in 1..20) FreeCompanySearch().scrapeFreeCompanySearch(
    name,
    communityFinder,
    world,
    dataCenter,
    activeMembers,
    focus,
    seeking,
    active,
    recruitment,
    housing,
    grandCompany,
    pages,
) else throw InvalidParameterException("`pages` must be at least 1 and less than or equal to 20.")

/**
 * Searches for a Free Company on *The Lodestone*.
 *
 * @param name The name of the Free Company to search for.
 * @param communityFinder Search for Free Company that are recruiting via the Community Finder.
 * @param world Search this [World] for Free Companies. Takes priority over [dataCenter].
 * @param dataCenter Search this [DataCenter] for Free Companies.
 * @param activeMembers Search for Free Companies with this many members.
 * @param focus Search for Free Companies with this focus. If `null`, all focuses are considered, including "Not specified". If a [Set] with a `null` element is provided, `null` is treated as "Not specified".
 * @param seeking Search for Free Companies who are seeking this role. If `null`, all roles are considered, including "Not specified". If a [Set] with a `null` element is provided, `null` is treated as "Not specified".
 * @param active Search for Free Companies that are active during this time.
 * @param recruitment Search for Free Companies with this recruitment state.
 * @param housing Search for Free Companies with this housing state.
 * @param grandCompany Search for Free Companies associated with this Grand Company.
 * @throws InvalidParameterException Thrown when [pages] is a value less than 1 or greater than 20.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
@JvmName("searchLodestoneFreeCompany")
@JvmOverloads
@Throws(InvalidParameterException::class, LodestoneException::class)
fun searchLodestoneFreeCompanyAsync(
    name: String? = null,
    communityFinder: Boolean = false,
    world: World? = null,
    dataCenter: DataCenter? = null,
    activeMembers: ActiveMembers? = null,
    focus: Set<Focus?>? = null,
    seeking: Set<Seeking?>? = null,
    active: Active? = null,
    recruitment: Recruitment? = null,
    housing: Housing? = null,
    grandCompany: Set<GrandCompanyName>? = null,
    pages: Byte = 1,
) = GlobalScope.future {
    searchLodestoneFreeCompany(
        name,
        communityFinder,
        world,
        dataCenter,
        activeMembers,
        focus,
        seeking,
        active,
        recruitment,
        housing,
        grandCompany,
        pages,
    )
}
