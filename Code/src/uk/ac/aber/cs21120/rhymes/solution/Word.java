package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import uk.ac.aber.cs21120.rhymes.interfaces.IWord;

import java.util.HashSet;
import java.util.Set;

public class Word implements IWord {

    String word;
    Set<IPronunciation> pronunciations = new HashSet<>();

    /**
     * Constructor that creates a word with no pronunciations
     * @param word is the English word we are creating
     */
    public Word (String word) {
        this.word = word;
    }

    /**
     * Getter method for the spelling of the created word
     * @return word
     */
    @Override
    public String getWord() {
        return word;
    }

    /**
     * Adds a pronunciation to the word by adding it to the hashset
     * @param pronunciation the pronunciation to add
     * @throws IllegalArgumentException if the pronunciation is null
     * - SIDE NOTE -
     * The assignment pdf specifies that a RunTimeException should be thrown
     * if the pronunciation is null, but the interfaces and tests specify and
     * check for an IllegalArgumentException. So instead of changing the interfaces
     * and tests, I have decided to just throw the exception they expect.
     */
    @Override
    public void addPronunciation(IPronunciation pronunciation) throws IllegalArgumentException {
        if (pronunciation == null) {
            throw new IllegalArgumentException("Pronunciation is null");
        }
        pronunciations.add(pronunciation);
    }

    /**
     * Returns a set of the words possible pronunciations
     * @return the pronunciations
     */
    @Override
    public Set<IPronunciation> getPronunciations() {
        return pronunciations;
    }
}
