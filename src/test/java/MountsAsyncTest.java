import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MountsAsyncTest {
    @Test
    void getMountsAsync() {
        assertDoesNotThrow(() -> System.out.println(KtLodestone.getMountsAsync(27545492).get()));
    }

    @Test
    void getInvalidMountsAsync() {
        assertThrows(CharacterNotFoundException.class, () -> {
            try {
                KtLodestone.getMountsAsync(0).get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        });
    }
}
