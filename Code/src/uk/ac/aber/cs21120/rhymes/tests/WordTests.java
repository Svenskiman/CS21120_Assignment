package uk.ac.aber.cs21120.rhymes.tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import uk.ac.aber.cs21120.rhymes.interfaces.IWord;
import uk.ac.aber.cs21120.rhymes.solution.Phoneme;
import uk.ac.aber.cs21120.rhymes.solution.Pronunciation;
import uk.ac.aber.cs21120.rhymes.solution.Word;

/**
 * Simple tests for a simple class.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WordTests {
    /**
     * Make sure we can construct a word and get the word back.
     */
    @Test
    @Order(0)
    void testConstruction() {
        IWord w = new Word("hello");
        assertEquals("hello", w.getWord());
    }

    /**
     * Make sure we cannot add a null pronunciation to a word.
     */
    @Test
    @Order(10)
    void testAddNullPronunciation() {
        IWord w = new Word("hello");
        assertThrows(IllegalArgumentException.class, () -> {
            w.addPronunciation(null);
        });
    }

    /**
     * Make sure we can add a pronunciation to a word.
     */
    @Test
    @Order(20)
    void testAddPronunciation() {
        IWord w = new Word("hello");

        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.AY, 1));
        w.addPronunciation(p);

        // and is there only one item is the set?
        assertEquals(1, w.getPronunciations().size());
        // get that item
        IPronunciation p2 = w.getPronunciations().iterator().next();
        // and check it is the same as the one we added. THIS TEST MIGHT FAIL
        // if we have done something weird like make copies of the phonemes
        // or the pronunciation.
        assertEquals(p, p2);
    }

    /**
     * Make sure we can add multiple pronunciations to a word.
     */
    @Test
    @Order(30)
    void testAddMultiplePronunciations() {
        IWord w = new Word("hello");

        IPronunciation p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.AY, 1));
        w.addPronunciation(p1);

        IPronunciation p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.OW, 1));
        w.addPronunciation(p2);

        // and are there only two items is the set?
        assertEquals(2, w.getPronunciations().size());

        // the previous test should suffice for checking that
        // the pronunciations are the same as the ones we added.
    }

    /**
     * Test that if we add the same pronunciation twice, it only appears once.
     */
    @Test
    @Order(40)
    void testAddSamePronunciationTwice() {
        IWord w = new Word("hello");

        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.AY, 1));
        w.addPronunciation(p);
        w.addPronunciation(p);

        // and is there only one item is the set?
        assertEquals(1, w.getPronunciations().size());
    }
}
