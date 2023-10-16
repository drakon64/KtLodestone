@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.InvalidParameterException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.linkshell.search.LinkshellSearch
import cloud.drakon.ktlodestone.search.ActiveMembers
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

/**
 * Searches for a Linkshell on The Lodestone.
 *
 * @param name The name of the Linkshell to search for.
 * @param communityFinder Search for Linkshells that are recruiting via the Community Finder.
 * @param world Search this [World] for Linkshells. Takes priority over [dataCenter].
 * @param dataCenter Search this [DataCenter] for Linkshells.
 * @param activeMembers Search for Linkshells with this many members.
 * @param crossWorld Search for Cross-world Linkshells.
 * @param pages The number of pages of characters to return. One page contains twenty characters.
 * @throws InvalidParameterException Thrown when [pages] is a value less than 1 or greater than 20.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
suspend fun searchLodestoneLinkshell(
    name: String? = null,
    communityFinder: Boolean = false,
    world: World? = null,
    dataCenter: DataCenter? = null,
    activeMembers: ActiveMembers? = null,
    crossWorld: Boolean = false,
    pages: Byte = 1,
) = if (pages in 1..20) LinkshellSearch().scrapeLinkshellSearch(
    name,
    communityFinder,
    world,
    dataCenter,
    activeMembers,
    crossWorld,
    pages,
) else throw InvalidParameterException("`pages` must be at least 1 and less than or equal to 20.")

/**
 * Searches for a Linkshell on The Lodestone.
 *
 * @param name The name of the Linkshell to search for.
 * @param communityFinder Search for Linkshells that are recruiting via the Community Finder.
 * @param world Search this [World] for Linkshells. Takes priority over [dataCenter].
 * @param dataCenter Search this [DataCenter] for Linkshells.
 * @param activeMembers Search for Linkshells with this many members.
 * @param crossWorld Search for Cross-world Linkshells.
 * @param pages The number of pages of characters to return. One page contains twenty characters.
 * @throws InvalidParameterException Thrown when [pages] is a value less than 1 or greater than 20.
 * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
 */
@JvmName("searchLodestoneLinkshell")
@JvmOverloads
@Throws(InvalidParameterException::class, LodestoneException::class)
fun searchLodestoneLinkshellAsync(
    name: String? = null,
    communityFinder: Boolean = false,
    world: World? = null,
    dataCenter: DataCenter? = null,
    activeMembers: ActiveMembers? = null,
    crossWorld: Boolean = false,
    pages: Byte = 1,
) = GlobalScope.future {
    searchLodestoneLinkshell(
        name,
        communityFinder,
        world,
        dataCenter,
        activeMembers,
        crossWorld,
        pages,
    )
}
