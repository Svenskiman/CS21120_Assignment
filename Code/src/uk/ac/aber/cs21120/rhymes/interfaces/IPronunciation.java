package uk.ac.aber.cs21120.rhymes.interfaces;

import java.util.List;

/**
 * Represents a pronunciation of a word in the CMU Pronouncing Dictionary.
 * Consists of a list of phonemes, which are themselves consist
 * of Arpabet enum and optional stress values.
 *
 * Initially, the list of phonemes will be empty, and you should
 * add to it with "add". This might seem an odd way to do things - it might
 * appear more natural to pass a list of phonemes to the constructor.
 * However, this would make it easy for a caller to modify the list - or worse,
 * for several pronunciations to share the same list.
 * Also, we would have to specify a constructor outside the interface, which
 * Java doesn't allow (and we've already had to do once!)
 */
public interface IPronunciation {

    /**
     * Add a phoneme to the end of the pronunciation.
     * @throws IllegalArgumentException if the phoneme is null
     * @param phoneme the phoneme to add
     */
    void add(IPhoneme phoneme);

    /**
     * Return a list of phonemes in this pronunciation.
     * @return a list of phonemes
     */
    List<IPhoneme> getPhonemes();

    /**
     * Return the index of the final stressed vowel in the pronunciation.
     * This will be the vowel in the pronunciation with the highest stress
     * value. If there are multiple vowels with the same highest stress value,
     * the index of the final one should be returned.
     *
     * - If no vowel is found, -1 should be returned.
     * - If no vowel is stressed, the index of the final vowel should be returned.
     *
     * @return the index of the final stressed vowel in the pronunciation or -1
     *
     */
    int findFinalStressedVowelIndex();

    /**
     * return true if this pronunciation rhymes with the other pronunciation.
     * This will be true if the pronunciations have the same phonemes from
     * the final stressed vowel onwards.
     *
     * @throws IllegalArgumentException if the other pronunciation is null
     *
     * @param other the other pronunciation to compare with
     * @return true if this pronunciation rhymes with the other pronunciation
     */
    boolean rhymesWith(IPronunciation other);
}
