package search;

import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.search.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CharacterSearchAsyncTest {
    @Test
    void getCharacterAsync() {
        assertDoesNotThrow(() -> System.out.println(KtLodestone.searchCharacterAsync("Kumokiri Yamitori", World.Cerberus).get()));
    }
}
