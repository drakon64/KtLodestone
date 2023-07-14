package profile

import cloud.drakon.ktlodestone.KtLodestone
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class GearSetTest {
    @Test fun getAttributes() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(KtLodestone.getGearSet(27545492))
            }
        }
    }

    @Test fun getInvalidGearSet() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(KtLodestone.getGearSet(0))
            }
        }
    }
}
