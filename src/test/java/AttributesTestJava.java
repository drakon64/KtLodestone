import cloud.drakon.ktlodestone.KtLodestone;
import cloud.drakon.ktlodestone.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AttributesTestJava {
    @Test
    void getCharacter() {
        assertDoesNotThrow(() -> KtLodestone.getAttributesBlocking(27545492));
    }

    @Test
    void getInvalidCharacter() {
        assertThrows(CharacterNotFoundException.class, () -> KtLodestone.getAttributesBlocking(0));
    }
}
