package profile;

import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CharacterScrapeAsyncTest {
    @Test
    void getCharacterAsync() {
        assertDoesNotThrow(() -> System.out.println(KtLodestone.getCharacterAsync(27545492).get()));
    }

    @Test
    void getInvalidCharacterAsync() {
        assertThrows(CharacterNotFoundException.class, () -> {
            try {
                KtLodestone.getCharacterAsync(0).get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        });
    }
}
