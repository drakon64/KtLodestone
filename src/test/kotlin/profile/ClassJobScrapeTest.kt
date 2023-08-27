package profile

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.Character.getClassJob
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class ClassJobScrapeTest {
    @Test fun getClassJobTest() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(getClassJob(27545492))
            }
        }
    }

    @Test fun getInvalidClassJob() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(getClassJob(0))
            }
        }
    }
}
