package uk.ac.aber.cs21120.rhymes.interfaces;

import java.util.Set;

/**
 * The interface to the dictionary of words and their pronunciations.
 */
public interface IDictionary {
    /**
     * Returns the word with the given spelling.
     * @param word the spelling of the word
     * @return the word or null if it is not in the dictionary
     */
    IWord getWord(String word);

    /**
     * Adds a word to the dictionary. If the word already exists,
     * IllegalArgumentException is thrown.
     * @param word the word to add
     * @throws IllegalArgumentException if the word already exists
     */
    void addWord(IWord word);

    /**
     * Returns the number of words in the dictionary.
     * @return the number of words
     */
    int getWordCount();

    /**
     * Returns the number of pronunciations in the dictionary.
     * @return the number of pronunciations
     */
    int getPronunciationCount();

    /**
     * Parse a line from the CMU Pronouncing Dictionary.
     */
    void parseDictionaryLine(String line);

    /**
     * Load the CMU Pronouncing Dictionary from a file.
     */
    void loadDictionary(String fileName);

    /**
     * Get all the rhymes for all pronunciations of a word (task 5)
     * Should throw an IllegalArgumentException if the word is not in the dictionary,
     * or is null.
     * @param word the word to get rhymes for
     * @return a set of words that rhyme with the given word
     */
    Set<String> getRhymes(String word);
}
