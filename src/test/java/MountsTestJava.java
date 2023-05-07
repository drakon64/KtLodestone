import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MountsTestJava {
    @Test
    void getMounts() {
        assertDoesNotThrow(() -> KtLodestone.getMountsBlocking(27545492));
    }

    @Test
    void getInvalidMounts() {
        assertThrows(CharacterNotFoundException.class, () -> KtLodestone.getMountsBlocking(0));
    }
}
