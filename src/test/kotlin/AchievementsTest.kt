import cloud.drakon.ktlodestone.KtLodestone
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class AchievementsTest {
    @Test fun getAchievements() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(KtLodestone.getAchievements(27545492, 1u))
            }
        }
    }

    @Test fun getInvalidAchievements() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(KtLodestone.getAchievements(0, 1u))
            }
        }
    }
}
