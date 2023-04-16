import cloud.drakon.ktlodestone.Character
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class CharacterTest {
    @Test fun getCharacter() {
        assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(Character.getCharacter(27545492, true))
            }
        }
    }

    @Test fun getInvalidCharacter() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(Character.getCharacter(0, false))
            }
        }
    }
}
