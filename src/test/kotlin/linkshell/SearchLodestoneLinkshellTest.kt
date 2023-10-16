package linkshell

import cloud.drakon.ktlodestone.exception.InvalidParameterException
import cloud.drakon.ktlodestone.searchLodestoneLinkshell
import cloud.drakon.ktlodestone.world.World
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

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

    @Test
    fun searchInvalidLodestoneCrossWorldLinkshellTest() {
        assertThrows<InvalidParameterException> {
            runBlocking {
                println(searchLodestoneLinkshell(world = World.Cerberus, crossWorld = true))
            }
        }
    }
}
