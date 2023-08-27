package profile

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.getGearSet
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class GearSetScrapeTest {
    @Test fun getAttributesTest() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(getGearSet(27545492))
            }
        }
    }

    @Test fun getInvalidGearSet() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(getGearSet(0))
            }
        }
    }
}
