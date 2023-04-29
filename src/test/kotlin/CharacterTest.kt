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
                println(
                    Character.getCharacter(
                        id = 27545492, attributes = true, gearSet = true
                    )
                )
            }
        }
    }

    @Test fun getInvalidCharacter() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(
                    Character.getCharacter(
                        id = 0, attributes = false, gearSet = false
                    )
                )
            }
        }
    }
}
