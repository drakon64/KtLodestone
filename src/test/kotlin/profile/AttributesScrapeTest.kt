package profile

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.getAttributes
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class AttributesScrapeTest {
    @Test fun getAttributesTest() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(getAttributes(27545492))
            }
        }
    }

    @Test fun getInvalidAttributes() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(getAttributes(0))
            }
        }
    }
}
