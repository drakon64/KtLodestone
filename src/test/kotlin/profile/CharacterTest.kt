package profile

import cloud.drakon.ktlodestone.KtLodestone
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class CharacterTest {
    @Test fun getCharacter() {
        assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(KtLodestone.getCharacter(27545492))
            }
        }
    }

    @Test fun getInvalidCharacter() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(KtLodestone.getCharacter(0))
            }
        }
    }
}
