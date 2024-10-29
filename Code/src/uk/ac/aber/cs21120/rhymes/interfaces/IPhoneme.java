package uk.ac.aber.cs21120.rhymes.interfaces;

/**
 * This interface represents phonemes as they occur in a pronunciation. Each
 * phoneme consists of an Arpabet enum and an optional stress value, which is
 * only present for vowel sounds (like IY - the "ee" sound in "beet").
 *
 * There must also be a constructor with the signature
 *
 *  Phoneme(Arpabet arpa, int stress)
 *
 * where the stress should be -1 if the phoneme is not a vowel.
 *
 */
public interface IPhoneme {
    /**
     * Returns the ARPABET phoneme.
     * @return the ARPABET phoneme
     */
    Arpabet getArpabet();

    /**
     * Get the optional stress value
     * @return the stress value, or -1 if the phoneme is not a vowel.
     */
    int getStress();

    /**
     * Returns true if the ARPABET value is the same as in the other phoneme. Stress is ignored.
     * @return true if the ARPABET value is the same as in the other phoneme.
     *
     * @throws IllegalArgumentException if the other phoneme is null.
     */
    boolean hasSameArpabet(IPhoneme other);

}
