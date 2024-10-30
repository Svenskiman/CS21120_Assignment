package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;
import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;

public class Phoneme implements IPhoneme {

    Arpabet phoneme;
    int stress;

    /**
     * Constructor for phoneme objects
     * @param phoneme is the phonemes ARPABET enum
     * @param stress is the phonemes stress value
     * @throws IllegalArgumentException when the provided phoneme is null, or when the provided stress
     * is out of range, or when the provided stress is not suitable for the provided phoneme type.
     */
    public Phoneme(Arpabet phoneme, int stress) throws IllegalArgumentException {

        this.phoneme = phoneme;
        this.stress = stress;

        if (phoneme == null) {
            throw new IllegalArgumentException("Phoneme is null");
        }
        if (stress < -1 || stress > 2) {
            throw new IllegalArgumentException("Stress is not in range");
        }
        else if (stress != -1 && !phoneme.isVowel()) {
            throw new IllegalArgumentException("Invalid stress when phoneme is not a vowel");
        }
        else if (stress == -1 && phoneme.isVowel()) {
            throw new IllegalArgumentException("Invalid stress when phoneme is a vowel");
        }

    }

    /**
     * Getter method for the phonemes ARPABET value
     * @return phoneme
     */
    @Override
    public Arpabet getArpabet() {
        return phoneme;
    }

    /**
     * Getter method for the phonemes stress
     * @return stress
     */
    @Override
    public int getStress() {
        return stress;
    }

    /**
     * Checks if two phonemes share the same ARPABET value
     * @param other is the phoneme object we are comparing to
     * @return true if both phonemes have the same ARPABET value
     * @throws IllegalArgumentException if the other phoneme is null
     */
    @Override
    public boolean hasSameArpabet(IPhoneme other) throws IllegalArgumentException {
        if (other == null) {
            throw new IllegalArgumentException("Value passed is null");
        }
        return other.getArpabet().equals(getArpabet());
    }
}
