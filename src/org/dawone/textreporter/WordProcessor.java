package org.dawone.textreporter;

import java.util.Arrays;

/**
 * Processes text by splitting it into individual words.
 *
 * <p>Words are separated by spaces and common punctuation marks.
 * The resulting words are trimmed, lowercased, and empty tokens are discarded.</p>
 */
public class WordProcessor {

    /**
     * Regular expression used to split text into words.
     */
    private static final String WORD_DELIMITER_REGEXP = "[ ,.'!]";

    /**
     * Splits the given text into an array of words.
     *
     * <p>The text is split using {@value #WORD_DELIMITER_REGEXP} as the delimiter.
     * Each resulting token is trimmed, lowercased, and empty tokens are discarded.</p>
     *
     * @param text the text to split into words; must not be {@code null}
     * @return an array of lowercase, non-empty words extracted from the text
     * @throws IllegalArgumentException if {@code text} is {@code null}
     */
    public String[] getWords(String text) {
        if (text == null) throw new IllegalArgumentException("text must not be null");

        return Arrays.stream(text.split(WORD_DELIMITER_REGEXP))
                .map(String::trim)
                .filter(word -> !word.isEmpty())
                .map(String::toLowerCase)
                .toArray(String[]::new);
    }

    public static void main(String[] args) {
        WordProcessor wordProcessor = new WordProcessor();
        for (String word : wordProcessor.getWords("This is a test, you'll get the words!")) {
            System.out.println(word);
        }
    }
}