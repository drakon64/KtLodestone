package profile;

import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AttributesAsyncTest {
    @Test
    void getAttributesAsync() {
        assertDoesNotThrow(() -> System.out.println(KtLodestone.getAttributesAsync(27545492).get()));
    }

    @Test
    void getInvalidAttributesAsync() {
        assertThrows(CharacterNotFoundException.class, () -> {
            try {
                KtLodestone.getAttributesAsync(0).get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        });
    }
}
