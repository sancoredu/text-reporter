import org.dawone.textreporter.FactOfTheDayLineReader;
import org.dawone.textreporter.TextReporter;
import org.dawone.textreporter.WordProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TextReporterIntegrationTest {

    private static final String REAL_API_ENDPOINT = "https://uselessfacts.jsph.pl//api/v2/facts/today";


    @Test
    void testWordProcessorBottomLevel() {
        WordProcessor wordProcessor = new WordProcessor();

        String[] words = wordProcessor.getWords("Hello, World! This is an API.");

        assertArrayEquals(new String[]{"hello", "world", "this", "is", "an", "api"}, words);

        assertThrows(IllegalArgumentException.class, () -> wordProcessor.getWords(null));
    }

    @Test
    void testFactReaderBottomLevelIntegration() {
        FactOfTheDayLineReader reader = new FactOfTheDayLineReader(REAL_API_ENDPOINT);

        String[] lines = reader.readLines();

        assertNotNull(lines);
        assertEquals(1, lines.length);
        assertFalse(lines[0].isEmpty());
    }

    @Test
    void testFactReaderInvalidEndpointThrowsException() {
        FactOfTheDayLineReader badReader = new FactOfTheDayLineReader("https://invalid.url.that.fails");
        assertThrows(RuntimeException.class, badReader::readLines);
    }


    @Test
    void testTextReporterTopLevelIntegration() {
        FactOfTheDayLineReader reader = new FactOfTheDayLineReader(REAL_API_ENDPOINT);
        WordProcessor processor = new WordProcessor();

        TextReporter reporter = new TextReporter(reader, processor);


        assertDoesNotThrow(() -> reporter.reportIsWordUsed("the"));

        boolean foundInventedWord = reporter.reportIsWordUsed("palabrainventada123xyz");
        assertFalse(foundInventedWord);
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "   "}) // Probamos valores vacíos o solo espacios
    void testTextReporterThrowsExceptionOnInvalidWord(String invalidWord) {
        FactOfTheDayLineReader reader = new FactOfTheDayLineReader(REAL_API_ENDPOINT);
        WordProcessor processor = new WordProcessor();
        TextReporter reporter = new TextReporter(reader, processor);

        assertThrows(IllegalArgumentException.class, () -> reporter.reportIsWordUsed(invalidWord));
        assertThrows(IllegalArgumentException.class, () -> reporter.reportIsWordUsed(null));
    }
}