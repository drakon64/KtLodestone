package profile

import cloud.drakon.ktlodestone.KtLodestone
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.PagesLessThanOneException
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class AchievementsScrapeTest {
    @Test fun getAchievements() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(KtLodestone.getAchievements(27545492, 2))
            }
        }
    }

    @Test fun getInvalidAchievements() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(KtLodestone.getAchievements(0, 2))
            }
        }
    }

    @Test fun getInvalidAchievementsPage() {
        assertThrows<PagesLessThanOneException> {
            return@assertThrows runBlocking {
                println(KtLodestone.getAchievements(27545492, 0))
            }
        }
    }
}
