package profile

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.Character.getMinions
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class MinionsScrapeTest {
    @Test fun getMinionsTest() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(getMinions(27545492))
            }
        }
    }

    @Test fun getInvalidMinions() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(getMinions(0))
            }
        }
    }
}
