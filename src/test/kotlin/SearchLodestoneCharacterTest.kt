import cloud.drakon.ktlodestone.searchLodestoneCharacter
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class SearchLodestoneCharacterTest {
    @Test
    fun searchLodestoneCharacterTest() = assertDoesNotThrow {
        runBlocking {
            println(searchLodestoneCharacter("Kumokiri Yamitori"))
        }
    }
}
