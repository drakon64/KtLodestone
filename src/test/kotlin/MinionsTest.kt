import cloud.drakon.ktlodestone.Minions
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class MinionsTest {
    @Test fun getMinions() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(Minions.getMinions(27545492))
            }
        }
    }

    @Test fun getInvalidMinions() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(Minions.getMinions(0))
            }
        }
    }
}
