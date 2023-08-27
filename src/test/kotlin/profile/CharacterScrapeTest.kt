package profile

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.getCharacter
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class CharacterScrapeTest {
    @Test fun getCharacterTest() {
        assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(getCharacter(27545492))
            }
        }
    }

    @Test fun getInvalidCharacter() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(getCharacter(0))
            }
        }
    }
}
