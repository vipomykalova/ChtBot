import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TruthOrDareTest {

    @Test
    void parseNameTest() {
        TruthOrDare game = new TruthOrDare();
        String[] names = {"Оксана", "Вика"};
        assertEquals(game.parseNames("Оксана,Вика"), names);
    }
}
