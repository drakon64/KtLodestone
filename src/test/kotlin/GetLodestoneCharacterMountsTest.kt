import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import cloud.drakon.ktlodestone.getLodestoneCharacterMounts
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class GetLodestoneCharacterMountsTest {
    @Test
    fun getLodestoneCharacterMountsTest() = assertDoesNotThrow {
        runBlocking {
            println(getLodestoneCharacterMounts(27545492))
        }
    }

    @Test
    fun getInvalidLodestoneCharacterMountsTest() {
        assertThrows<LodestoneNotFoundException> {
            runBlocking {
                getLodestoneCharacterMounts(-1)
            }
        }
    }
}
