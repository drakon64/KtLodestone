import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MinionsTestJava {
    @Test
    void getCharacter() {
        assertDoesNotThrow(() -> KtLodestone.getMinionsBlocking(27545492));
    }

    @Test
    void getInvalidCharacter() {
        assertThrows(CharacterNotFoundException.class, () -> KtLodestone.getMinionsBlocking(0));
    }
}
