package profile

import cloud.drakon.ktlodestone.KtLodestone
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class MountsTest {
    @Test fun getMounts() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(KtLodestone.getMounts(27545492))
            }
        }
    }

    @Test fun getInvalidMounts() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(KtLodestone.getMounts(0))
            }
        }
    }
}
