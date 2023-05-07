import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClassJobTestJava {
    @Test
    void getCharacter() {
        assertDoesNotThrow(() -> KtLodestone.getClassJobBlocking(27545492));
    }

    @Test
    void getInvalidCharacter() {
        assertThrows(CharacterNotFoundException.class, () -> KtLodestone.getClassJobBlocking(0));
    }
}
