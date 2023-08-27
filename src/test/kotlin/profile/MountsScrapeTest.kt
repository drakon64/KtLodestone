package profile

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.Character.getMounts
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class MountsScrapeTest {
    @Test fun getMountsTest() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(getMounts(27545492))
            }
        }
    }

    @Test fun getInvalidMountsTest() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(getMounts(0))
            }
        }
    }
}
