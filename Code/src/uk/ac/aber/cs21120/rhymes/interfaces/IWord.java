package uk.ac.aber.cs21120.rhymes.interfaces;

import java.util.Set;

/**
 * This class represents a word and the different ways it can be pronounced.
 *
 * A class which implements this interface must have a constructor with the signature
 *      Word(String word)
 * which creates a new word with no pronunciations and sets its English spelling.
 *
 */
public interface IWord {
    /**
     * Returns the standard English spelling of the word, as it was created.
     * @return the word
     */
    String getWord();

    /**
     * Add a pronunciation to the word
     * @throws IllegalArgumentException if the pronunciation is null
     * @param pronunciation the pronunciation to add
     */
    void addPronunciation(IPronunciation pronunciation);

    /**
     * Returns the possible pronunciations of the word.
     * @return the pronunciations
     */
    Set<IPronunciation> getPronunciations();
}
