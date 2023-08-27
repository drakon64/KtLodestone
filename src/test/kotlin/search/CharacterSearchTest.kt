package search

import cloud.drakon.ktlodestone.search.World
import cloud.drakon.ktlodestone.searchCharacter
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CharacterSearchTest {
    @Test fun searchCharacterTest() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(
                    searchCharacter(
                        "Kumokiri Yamitori", World.Cerberus
                    )
                )
            }
        }
    }
}
