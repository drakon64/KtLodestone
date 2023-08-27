@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.search.CharacterSearch
import cloud.drakon.ktlodestone.search.World
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.future

object Search {
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
}
