package profile;

import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MinionsAsyncTest {
    @Test
    void getMinionsAsync() {
        assertDoesNotThrow(() -> System.out.println(KtLodestone.getMinionsAsync(27545492).get()));
    }

    @Test
    void getInvalidMinionsAsync() {
        assertThrows(CharacterNotFoundException.class, () -> {
            try {
                KtLodestone.getMinionsAsync(0).get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        });
    }
}
