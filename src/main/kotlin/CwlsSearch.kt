@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.cwls.search.CrossWorldLinkshellSearch
import cloud.drakon.ktlodestone.exception.InvalidParameterException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.search.ActiveMembers
import cloud.drakon.ktlodestone.world.DataCenter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

/**
 * Searches for a Cross-world Linkshell on *The Lodestone*.
 *
 * @param name The name of the Cross-world Linkshell to search for.
 * @param communityFinder Search for Cross-world Linkshells that are recruiting via the Community Finder.
 * @param dataCenter Search this [DataCenter] for Cross-world Linkshells.
 * @param activeMembers Search for Cross-world Linkshells with this many members.
 * @param pages The number of pages of characters to return. One page contains twenty characters.
 * @throws InvalidParameterException Thrown when [pages] is a value less than 1 or greater than 20.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
suspend fun searchLodestoneCrossWorldLinkshell(
    name: String? = null,
    communityFinder: Boolean = false,
    dataCenter: DataCenter? = null,
    activeMembers: ActiveMembers? = null,
    pages: Byte = 1,
) = if (pages in 1..20) CrossWorldLinkshellSearch().scrapeCrossWorldLinkshellSearch(
    name,
    communityFinder,
    dataCenter,
    activeMembers,
    pages,
) else throw InvalidParameterException("`pages` must be at least 1 and less than or equal to 20.")

/**
 * Searches for a Cross-world Linkshell on *The Lodestone*.
 *
 * @param name The name of the Cross-world Linkshell to search for.
 * @param communityFinder Search for Cross-world Linkshells that are recruiting via the Community Finder.
 * @param dataCenter Search this [DataCenter] for Cross-world Linkshells.
 * @param activeMembers Search for Cross-world Linkshells with this many members.
 * @param pages The number of pages of characters to return. One page contains twenty characters.
 * @throws InvalidParameterException Thrown when [pages] is a value less than 1 or greater than 20.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
@JvmName("searchLodestoneCrossWorldLinkshell")
@JvmOverloads
@Throws(InvalidParameterException::class, LodestoneException::class)
fun searchLodestoneCrossWorldLinkshellAsync(
    name: String? = null,
    communityFinder: Boolean = false,
    dataCenter: DataCenter? = null,
    activeMembers: ActiveMembers? = null,
    pages: Byte = 1,
) = GlobalScope.future {
    searchLodestoneCrossWorldLinkshell(
        name,
        communityFinder,
        dataCenter,
        activeMembers,
        pages,
    )
}
