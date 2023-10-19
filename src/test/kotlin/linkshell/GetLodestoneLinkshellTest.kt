package linkshell

import cloud.drakon.ktlodestone.getLodestoneLinkshell
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class GetLodestoneLinkshellTest {
    @Test
    fun getLodestoneCrossWorldLinkshellTest() = assertDoesNotThrow {
        runBlocking {
            println(
                getLodestoneLinkshell(
                    "307846eb43875f693b5fa713f2cdf57e79b8e74e", crossWorld = true
                )
            )
        }
    }
}
