import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GearSetAsyncTest {
    @Test
    void getGearSetAsync() {
        assertDoesNotThrow(() -> System.out.println(KtLodestone.getGearSetAsync(27545492).get()));
    }

    @Test
    void getInvalidGearSetAsync() {
        assertThrows(CharacterNotFoundException.class, () -> {
            try {
                KtLodestone.getGearSetAsync(0).get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        });
    }
}
