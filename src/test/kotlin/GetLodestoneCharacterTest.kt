import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import cloud.drakon.ktlodestone.getLodestoneCharacter
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class GetLodestoneCharacterTest {
    @Test
    fun getLodestoneCharacterTest() = assertDoesNotThrow {
        runBlocking {
            println(getLodestoneCharacter(27545492))
        }
    }

    @Test
    fun getInvalidLodestoneCharacterTest() {
        assertThrows<LodestoneNotFoundException> {
            runBlocking {
                getLodestoneCharacter(-1)
            }
        }
    }
}
