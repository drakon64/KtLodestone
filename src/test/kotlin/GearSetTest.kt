import cloud.drakon.ktlodestone.GearSet
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class GearSetTest {
    @Test fun getAttributes() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(GearSet.getGearSet(27545492))
            }
        }
    }

    @Test fun getInvalidCharacter() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(GearSet.getGearSet(0))
            }
        }
    }
}
