import org.dawone.textreporter.FactOfTheDayLineReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FactOfTheDayLineReaderTest {

    @Test
    void readLines_UrlValida_DvuelveFact() {
        FactOfTheDayLineReader lector = new FactOfTheDayLineReader("https://uselessfacts.jsph.pl//api/v2/facts/today");

        String[] resultado = lector.readLines();

        boolean tieneUnaLinea = resultado.length == 1;
        assertTrue(tieneUnaLinea);
        assertTrue(resultado[0].trim().length() > 0);
    }

    @Test
    void readLines_UrlInvalida_LanzaExcepcion() {
        FactOfTheDayLineReader lector = new FactOfTheDayLineReader("http://url-inventada-que-falla.com");

        assertThrows(RuntimeException.class, () -> lector.readLines());
    }

    @Test
    void readLines_UrlNula_ProduceNullPointerException() {
        FactOfTheDayLineReader lector = new FactOfTheDayLineReader(null);

        assertThrows(NullPointerException.class, () -> lector.readLines());
    }
}