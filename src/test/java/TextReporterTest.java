import org.dawone.textreporter.FactOfTheDayLineReader;
import org.dawone.textreporter.TextReporter;
import org.dawone.textreporter.WordProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class TextReporterTest {

    @Test
    void reportIsWordUsed_PalabraNulaOVacia_LanzaExcepcion() {
        FactOfTheDayLineReader lectorMock = Mockito.mock(FactOfTheDayLineReader.class);
        WordProcessor procesadorReal = new WordProcessor(); // Usamos la instancia inferior real (Bottom-Up)

        TextReporter reporter = new TextReporter(lectorMock, procesadorReal);

        assertThrows(IllegalArgumentException.class, () -> reporter.reportIsWordUsed(null));
        assertThrows(IllegalArgumentException.class, () -> reporter.reportIsWordUsed(""));
    }

    @Test
    void reportIsWordUsed_CoincidenciaDePalabra_DevuelveVerdadero() {
        FactOfTheDayLineReader lectorMock = Mockito.mock(FactOfTheDayLineReader.class);
        Mockito.doReturn(new String[]{"The quick brown fox"}).when(lectorMock).readLines();

        WordProcessor procesadorReal = new WordProcessor();
        TextReporter reporter = new TextReporter(lectorMock, procesadorReal);

        boolean resultado = reporter.reportIsWordUsed("fox");

        assertEquals(true, resultado);
    }

    @Test
    void reportIsWordUsed_SinCoincidenciaDePalabra_DevuelveFalso() {
        FactOfTheDayLineReader lectorMock = Mockito.mock(FactOfTheDayLineReader.class);
        Mockito.doReturn(new String[]{"A completely different sentence"}).when(lectorMock).readLines();

        WordProcessor procesadorReal = new WordProcessor();
        TextReporter reporter = new TextReporter(lectorMock, procesadorReal);

        boolean resultado = reporter.reportIsWordUsed("fox");

        assertEquals(false, resultado);
    }

    @Test
    void reportIsWordUsed_TextoDeLaApiVacio_RetornaFalso() {
        FactOfTheDayLineReader lectorMock = Mockito.mock(FactOfTheDayLineReader.class);
        Mockito.doReturn(new String[]{}).when(lectorMock).readLines();

        WordProcessor procesadorReal = new WordProcessor();
        TextReporter reporter = new TextReporter(lectorMock, procesadorReal);

        boolean resultado = reporter.reportIsWordUsed("anyword");

        assertEquals(false, resultado);
    }
}