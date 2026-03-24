import org.dawone.textreporter.WordProcessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordProcessorTest {

    @Test
    void getWords_TextoNulo_LanzaExcepcion() {
        WordProcessor procesador = new WordProcessor();

        assertThrows(IllegalArgumentException.class, () -> {
            procesador.getWords(null);
        });
    }

    @Test
    void getWords_TextoConMayusculas_DevuelveMinusculas() {
        WordProcessor procesador = new WordProcessor();

        String[] resultado = procesador.getWords("Un Ejemplo");

        assertEquals(2, resultado.length);
        assertEquals("un", resultado[0]);
        assertEquals("ejemplo", resultado[1]);
    }

    @Test
    void getWords_TextoConSimbolos_DevuelveSinSimbolos() {
        WordProcessor procesador = new WordProcessor();

        String[] resultado = procesador.getWords("Hello, world! It's a test.");

        assertArrayEquals(new String[]{"hello", "world", "it", "s", "a", "test"}, resultado);
    }

    @Test
    void getWords_SoloEspaciosYSimbolos_DevuelveArrayVacio() {
        WordProcessor procesador = new WordProcessor();

        String[] resultado = procesador.getWords("   , . !  '  ");

        assertEquals(0, resultado.length);
        assertArrayEquals(new String[]{}, resultado);
    }
}