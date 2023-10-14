@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.character.achievements.AchievementsSearch
import cloud.drakon.ktlodestone.exception.InvalidParameterException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

/**
 * Returns the achievements unlocked by a character with ID [id] from *The Lodestone*. This matches what is found on *The Lodestone*'s `/character/achievement` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on *The Lodestone*.
 * @throws InvalidParameterException Thrown when [pages] is a value less than 1.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
suspend fun getLodestoneCharacterAchievements(
    id: Int,
    pages: Byte = 1,
) = if (pages >= 1) AchievementsSearch().scrapeCharacterAchievements(
    id,
    pages
) else throw InvalidParameterException("`pages` must be at least 1.")

/**
 * Returns the achievements unlocked by a character with ID [id] from *The Lodestone*. This matches what is found on *The Lodestone*'s `/character/achievement` endpoint.

 * @throws LodestoneNotFoundException Thrown when the character isn't found on *The Lodestone*.
 * @throws InvalidParameterException Thrown when [pages] is a value less than 1.
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
@JvmName("getLodestoneCharacterAchievements")
@JvmOverloads
@Throws(LodestoneNotFoundException::class, InvalidParameterException::class, LodestoneException::class)
fun getLodestoneCharacterAchievementsAsync(id: Int, pages: Byte = 1) = GlobalScope.future {
    getLodestoneCharacterAchievements(id, pages)
}
