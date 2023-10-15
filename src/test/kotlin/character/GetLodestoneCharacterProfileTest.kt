package character

import cloud.drakon.ktlodestone.exception.LodestoneNotFoundException
import cloud.drakon.ktlodestone.getLodestoneCharacterProfile
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class GetLodestoneCharacterProfileTest {
    @Test
    fun getLodestoneCharacterProfileTest() = assertDoesNotThrow {
        runBlocking {
            println(getLodestoneCharacterProfile(27545492))
        }
    }

    @Test
    fun getInvalidLodestoneCharacterProfileTest() {
        assertThrows<LodestoneNotFoundException> {
            runBlocking {
                getLodestoneCharacterProfile(-1)
            }
        }
    }
}
