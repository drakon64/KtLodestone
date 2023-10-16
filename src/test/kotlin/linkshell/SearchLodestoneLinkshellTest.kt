package linkshell

import cloud.drakon.ktlodestone.searchLodestoneLinkshell
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class SearchLodestoneLinkshellTest {
    @Test
    fun searchLodestoneLinkshellTest() = assertDoesNotThrow {
        runBlocking {
            println(searchLodestoneLinkshell())
        }
    }

    @Test
    fun searchLodestoneCrossWorldLinkshellTest() = assertDoesNotThrow {
        runBlocking {
            println(searchLodestoneLinkshell(crossWorld = true))
        }
    }
}
