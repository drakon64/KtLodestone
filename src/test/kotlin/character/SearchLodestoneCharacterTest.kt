package character

import cloud.drakon.ktlodestone.searchLodestoneCharacter
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class SearchLodestoneCharacterTest {
    @Test
    fun searchLodestoneCharacterTest() = assertDoesNotThrow {
        runBlocking {
            println(searchLodestoneCharacter("Kumokiri Yamitori"))
        }
    }

    @Test
    fun searchLodestoneCharacterMultiTest() = assertDoesNotThrow {
        runBlocking {
            println(searchLodestoneCharacter(pages = 2))
        }
    }
}
