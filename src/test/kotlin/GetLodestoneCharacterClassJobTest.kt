import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import cloud.drakon.ktlodestone.getLodestoneCharacterClassJob
import cloud.drakon.ktlodestone.getLodestoneCharacterProfile
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class GetLodestoneCharacterClassJobTest {
    @Test
    fun getLodestoneCharacterClassJobTest() = assertDoesNotThrow {
        runBlocking {
            println(getLodestoneCharacterClassJob(27545492))
        }
    }

    @Test
    fun getInvalidLodestoneCharacterClassJobTest() {
        assertThrows<LodestoneNotFoundException> {
            runBlocking {
                getLodestoneCharacterClassJob(-1)
            }
        }
    }
}
