package search

import cloud.drakon.ktlodestone.KtLodestone
import cloud.drakon.ktlodestone.search.World
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CharacterSearchTest {
    @Test fun searchCharacter() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(
                    KtLodestone.searchCharacter(
                        "Kumokiri Yamitori", World.Cerberus
                    )
                )
            }
        }
    }
}
