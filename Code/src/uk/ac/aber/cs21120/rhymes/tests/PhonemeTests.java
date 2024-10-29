package uk.ac.aber.cs21120.rhymes.tests;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;
import uk.ac.aber.cs21120.rhymes.solution.Phoneme;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PhonemeTests {
    /**
     * Test that we can construct a Phoneme object.
     * This is a very, very basic test that just checks that the constructor
     * doesn't crash.
     */
    @Test
    @Order(10)
    void testConstruction() {
        IPhoneme p = new Phoneme(Arpabet.AH, 0);
    }

    /**
     * Make sure that the constructor throws an exception if the stress is out of range,
     * and doesn't when it is in range.
     */
    @Test
    @Order(20)
    void testStressOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            IPhoneme p = new Phoneme(Arpabet.AH, 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            IPhoneme p = new Phoneme(Arpabet.AH, -2);
        });

        new Phoneme(Arpabet.AH, 0);
        new Phoneme(Arpabet.AH, 1);
        new Phoneme(Arpabet.AH, 2);
    }


    /**
     * Test that creating a phoneme with a null Arpabet value throws an exception
     */
    @Test
    @Order(25)
    public void testNullPhoneme() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Phoneme(null, 0);
        });
    }


    /**
     * Make sure that the constructor throws an exception if the stress is not -1
     * for any consonant
     */
    @Test
    @Order(30)
    void testStressOnConsonantException() {
        for(Arpabet a : Arpabet.values()) {
            // for every consonant...
            if(!a.isVowel()) {
                // ...make sure a stress which isn't -1 throws an exception
                assertThrows(IllegalArgumentException.class, () -> {
                    new Phoneme(a, 0);
                });
                assertThrows(IllegalArgumentException.class, () -> {
                    new Phoneme(a, 1);
                });
                assertThrows(IllegalArgumentException.class, () -> {
                    new Phoneme(a, 2);
                });
            }
        }
    }

    /**
     * Make sure that the constructor throws an exception if the stress is -1
     * for any vowel
     */
    @Test
    @Order(40)
    void testNoStressOnVowelException() {
        for(Arpabet a : Arpabet.values()) {
            // for every vowel...
            if(a.isVowel()) {
                // ...make sure a stress which is -1 throws an exception
                assertThrows(IllegalArgumentException.class, () -> {
                    new Phoneme(a, -1);
                });
            }
        }
    }

    /**
     * Make sure we can get the phoneme back out of the object
     */
    @Test
    @Order(50)
    void testGetPhoneme() {
        // we test some random vowels and consonants
        IPhoneme p = new Phoneme(Arpabet.AH, 0);
        assertEquals(Arpabet.AH, p.getArpabet());
        p = new Phoneme(Arpabet.IY, 1);
        assertEquals(Arpabet.IY, p.getArpabet());
        p = new Phoneme(Arpabet.B, -1);
        assertEquals(Arpabet.B, p.getArpabet());
        p = new Phoneme(Arpabet.D, -1);
        assertEquals(Arpabet.D, p.getArpabet());

        // this is a sanity check - we now test ALL vowels and consonants.
        // If it passes the above checks and fails down here, that means
        // things aren't being done consistently.
        for(Arpabet a : Arpabet.values()) {
            // we have to create the object differently depending on whether it's a vowel or consonant
            p = new Phoneme(a, a.isVowel() ? 1 : -1);
            assertEquals(a, p.getArpabet());
        }
    }

    /**
     * Make sure we can get the stress back out of the object
     */
    @Test
    @Order(60)
    void testGetStress() {
        // we test some random vowels and consonants
        IPhoneme p = new Phoneme(Arpabet.AH, 0);
        assertEquals(0, p.getStress());
        p = new Phoneme(Arpabet.IY, 1);
        assertEquals(1, p.getStress());
        p = new Phoneme(Arpabet.B, -1);
        assertEquals(-1, p.getStress());
        p = new Phoneme(Arpabet.D, -1);
        assertEquals(-1, p.getStress());

        // this is a sanity check - we now test ALL vowels and consonants.
        // If it passes the above checks and fails down here, that means
        // things aren't being done consistently.
        for (Arpabet a : Arpabet.values()) {
            int stress;
            if (a.isVowel()) {
                // pick a stress from 0-2 based on the index of the vowel in the enum
                stress = a.ordinal() % 3;
            } else {
                stress = -1;    // must be -1 for consonants
            }

            // we have to create the object differently depending on whether it's a vowel or consonant
            p = new Phoneme(a, stress);
            assertEquals(stress, p.getStress());
        }
    }

    /**
     * Test that hasSamePhoneme returns true when the phonemes are the same
     * and false when they are different.
     */
    @Test
    @Order(80)
    public void testHasSameArpabet() {
        IPhoneme p1 = new Phoneme(Arpabet.AH, 0);
        IPhoneme p2 = new Phoneme(Arpabet.AH, 0);
        assertTrue(p1.hasSameArpabet(p2));

        // same phoneme, different stress
        p2 = new Phoneme(Arpabet.AH, 2);
        assertTrue(p1.hasSameArpabet(p2));

        // different phoneme, same stress
        p2 = new Phoneme(Arpabet.IY, 0);
        assertFalse(p1.hasSameArpabet(p2));

        // different both
        p2 = new Phoneme(Arpabet.IY, 1);
        assertFalse(p1.hasSameArpabet(p2));
        p2 = new Phoneme(Arpabet.B, -1);
        assertFalse(p1.hasSameArpabet(p2));
        p2 = new Phoneme(Arpabet.D, -1);
        assertFalse(p1.hasSameArpabet(p2));

        // consonants
        p1 = new Phoneme(Arpabet.B, -1);
        p2 = new Phoneme(Arpabet.B, -1);
        assertTrue(p1.hasSameArpabet(p2));
        p2 = new Phoneme(Arpabet.D, -1);
        assertFalse(p1.hasSameArpabet(p2));

    }

    /**
     * Test that we can't pass null to hasSamePhoneme
     */
    @Test
    @Order(90)
    public void testHasSameArpabetNull() {
        IPhoneme p1 = new Phoneme(Arpabet.AH, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            p1.hasSameArpabet(null);
        });
    }
}
