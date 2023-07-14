package profile

import cloud.drakon.ktlodestone.KtLodestone
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class ClassJobTest {
    @Test fun getClassJob() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(KtLodestone.getClassJob(27545492))
            }
        }
    }

    @Test fun getInvalidClassJob() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(KtLodestone.getClassJob(0))
            }
        }
    }
}
