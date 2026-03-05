package org.dawone.textreporter;

/**
 * Reports information about word usage in the daily fact retrieved from an external source.
 *
 * <p>Uses a {@link FactOfTheDayLineReader} to retrieve the fact and a
 * {@link WordProcessor} to parse its words.</p>
 */
public class TextReporter {

    private final FactOfTheDayLineReader reader;
    private final WordProcessor wordProcessor;

    /**
     * Constructs a {@code TextReporter} with the given reader and word processor.
     *
     * @param reader        the reader used to retrieve lines from the fact of the day
     * @param wordProcessor the processor used to extract words from each line
     */
    public TextReporter(FactOfTheDayLineReader reader, WordProcessor wordProcessor) {
        this.reader = reader;
        this.wordProcessor = wordProcessor;
    }

    /**
     * Reports whether a given word appears in the fact of the day.
     *
     * <p>The check is case-insensitive.</p>
     *
     * @param wordToCheck the word to search for
     * @return {@code true} if the word is found, {@code false} otherwise
     */
    public boolean reportIsWordUsed(String wordToCheck) {
        if (wordToCheck == null || wordToCheck.trim().isEmpty()) {
            throw new IllegalArgumentException("wordToCheck must not be null or empty");
        }

        for (String line : reader.readLines()) {
            for (String word : wordProcessor.getWords(line)) {
                if (word.equals(wordToCheck.toLowerCase())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String apiEndpoint = "https://uselessfacts.jsph.pl//api/v2/facts/today";
        FactOfTheDayLineReader reader = new FactOfTheDayLineReader(apiEndpoint);
        WordProcessor wordProcessor = new WordProcessor();
        TextReporter textReporter = new TextReporter(reader, wordProcessor);

        String wordToCheck = "the";
        boolean isUsed = textReporter.reportIsWordUsed(wordToCheck);
        System.out.println("The word '" + wordToCheck + "' " + (isUsed ? "is" : "is not") + " used in the fact of the day.");
    }
}