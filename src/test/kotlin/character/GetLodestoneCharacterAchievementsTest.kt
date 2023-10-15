package character

import cloud.drakon.ktlodestone.getLodestoneCharacterAchievements
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class GetLodestoneCharacterAchievementsTest {
    @Test
    fun getLodestoneCharacterAchievementsTest() = assertDoesNotThrow {
        runBlocking {
            println(getLodestoneCharacterAchievements(27545492))
        }
    }

    @Test
    fun getLodestoneCharacterAchievementsMultiTest() = assertDoesNotThrow {
        runBlocking {
            println(getLodestoneCharacterAchievements(27545492, pages = 2))
        }
    }
}
