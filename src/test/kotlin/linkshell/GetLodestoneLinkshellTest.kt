package linkshell

import cloud.drakon.ktlodestone.getLodestoneLinkshell
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class GetLodestoneLinkshellTest {
    @Test
    fun getLodestoneLinkshellTest() = assertDoesNotThrow {
        runBlocking {
            println(
                getLodestoneLinkshell("18858823439650456")
            )
        }
    }

    @Test
    fun getLodestoneCrossWorldLinkshellTest() = assertDoesNotThrow {
        runBlocking {
            println(
                getLodestoneLinkshell(
                    "840904981f91106fd9412595bc9b4f097a876296", crossWorld = true
                )
            )
        }
    }
}
