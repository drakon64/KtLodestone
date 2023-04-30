import cloud.drakon.ktlodestone.Attributes
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class AttributesTest {
    @Test fun getAttributes() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(Attributes.getAttributes(27545492))
            }
        }
    }

    @Test fun getInvalidCharacter() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(Attributes.getAttributes(0))
            }
        }
    }
}
