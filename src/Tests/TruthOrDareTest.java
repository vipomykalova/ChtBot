package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import TruthOrDare.TruthOrDare;

class TruthOrDareTest {

    @Test
    void parseNameTest() {
        TruthOrDare game = new TruthOrDare();
        String[] names = {"Оксана", "Вика"};
        String[] result = game.parseNames("Оксана,Вика");
        assertEquals(result[0], names[0]);
        assertEquals(result[1], names[1]);
    }

    @Test
    void
}
