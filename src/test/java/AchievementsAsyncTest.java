import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AchievementsAsyncTest {
    @Test
    void getAchievementsAsync() {
        assertDoesNotThrow(() -> System.out.println(KtLodestone.getAchievementsAsync(27545492, (byte) 1).get()));
    }

    @Test
    void getInvalidAchievementsAsync() {
        assertThrows(CharacterNotFoundException.class, () -> {
            try {
                KtLodestone.getAchievementsAsync(0, (byte) 1).get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        });
    }
}
