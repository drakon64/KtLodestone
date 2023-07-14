package profile;

import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClassJobAsyncTest {
    @Test
    void getClassJobAsync() {
        assertDoesNotThrow(() -> System.out.println(KtLodestone.getClassJobAsync(27545492).get()));
    }

    @Test
    void getInvalidClassJobAsync() {
        assertThrows(CharacterNotFoundException.class, () -> {
            try {
                KtLodestone.getClassJobAsync(0).get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        });
    }
}
