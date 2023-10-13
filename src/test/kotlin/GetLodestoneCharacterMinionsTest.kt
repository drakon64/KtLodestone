import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import cloud.drakon.ktlodestone.getLodestoneCharacterMinions
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class GetLodestoneCharacterMinionsTest {
    @Test
    fun getLodestoneCharacterMinionsTest() = assertDoesNotThrow {
        runBlocking {
            println(getLodestoneCharacterMinions(27545492))
        }
    }

    @Test
    fun getInvalidLodestoneCharacterMinionsTest() {
        assertThrows<LodestoneNotFoundException> {
            runBlocking {
                getLodestoneCharacterMinions(-1)
            }
        }
    }
}
