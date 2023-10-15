package cwls

import cloud.drakon.ktlodestone.searchLodestoneCrossWorldLinkshell
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class SearchLodestoneCrossWorldLinkshellTest {
    @Test
    fun searchLodestoneCrossWorldLinkshellTest() = assertDoesNotThrow {
        runBlocking {
            println(searchLodestoneCrossWorldLinkshell())
        }
    }
}
