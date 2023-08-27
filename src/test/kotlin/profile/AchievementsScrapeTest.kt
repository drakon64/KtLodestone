package profile

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.PagesLessThanOneException
import cloud.drakon.ktlodestone.getAchievements
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class AchievementsScrapeTest {
    @Test fun getAchievementsTest() {
        Assertions.assertDoesNotThrow {
            return@assertDoesNotThrow runBlocking {
                println(getAchievements(27545492, 2))
            }
        }
    }

    @Test fun getInvalidAchievementsTest() {
        assertThrows<CharacterNotFoundException> {
            return@assertThrows runBlocking {
                println(getAchievements(0, 2))
            }
        }
    }

    @Test fun getInvalidAchievementsPageTest() {
        assertThrows<PagesLessThanOneException> {
            return@assertThrows runBlocking {
                println(getAchievements(27545492, 0))
            }
        }
    }
}
