package uk.ac.aber.cs21120.rhymes.tests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IDictionary;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import uk.ac.aber.cs21120.rhymes.solution.Dictionary;
import uk.ac.aber.cs21120.rhymes.solution.Phoneme;
import uk.ac.aber.cs21120.rhymes.solution.Pronunciation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the rhyming parts of the assignment: Pronunciation.rhymesWith() first,
 * and then Dictionary.getRhymes().
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RhymeTests {

    //
    // The first part of this class tests Pronunciation.rhymesWith.
    // Because it works with Pronunciation objects, it's quite tedious to set up the tests.
    // The later Dictionary tests are simpler to write because they work with words, and so they
    // may be more thorough.
    //

    /**
     * Trivial test of single vowel sounds with the same stress
     */
    @Test
    @Order(0)
    void  testRhymesWith_OnePhonemeSameStress(){
        IPronunciation p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.AA, 0));
        IPronunciation p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.AA, 0));
        assertTrue(p1.rhymesWith(p2));

        // change the second sound - they should not rhyme now
        p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.AE, 0));
        assertFalse(p1.rhymesWith(p2));
    }

    /**
     * test of single vowel sounds with different stress
     */

    @Test
    @Order(10)
    void  testRhymesWith_OnePhonemeDifferentStress(){
        // despite the different stress, they should rhyme. If this fails, but the previous
        // test passed, then you may be taking account of the stress in the rhyme test.
        IPronunciation p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.AA, 0));
        IPronunciation p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.AA, 1));
        assertTrue(p1.rhymesWith(p2));

        // change the second sound - they should not rhyme now.
        p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.AE, 1));
        assertFalse(p1.rhymesWith(p2));
    }

    /**
     * Test a single syllable - consonant and vowel. We'll start with "kaa" and "baa"
     * (how "car" and "bar" might be pronounced in some parts of the UK)
     */
    @Test
    @Order(20)
    void  testRhymesWith_OneSyllableCV() {
        IPronunciation p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.K, -1));
        p1.add(new Phoneme(Arpabet.AA, 0));
        IPronunciation p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.B, -1));
        p2.add(new Phoneme(Arpabet.AA, 0));
        // they should rhyme
        assertTrue(p1.rhymesWith(p2));

        // again, change the vowel - now they should not rhyme
        p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.B, -1));
        p2.add(new Phoneme(Arpabet.AE, 0));
        assertFalse(p1.rhymesWith(p2));
    }

    /**
     * Test a single syllable, but with a trailing consonant. We'll try "cat" and "bat".
     */
    @Test
    @Order(30)
    void  testRhymesWith_OneSyllableCVC() {
        IPronunciation p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.K, -1));
        p1.add(new Phoneme(Arpabet.AE, 0));
        p1.add(new Phoneme(Arpabet.T, -1));
        IPronunciation p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.B, -1));
        p2.add(new Phoneme(Arpabet.AE, 0));
        p2.add(new Phoneme(Arpabet.T, -1));
        // they should rhyme
        assertTrue(p1.rhymesWith(p2));

        // change the vowel so we are now looking at "cat" and "bit" - now they should not rhyme
        p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.B, -1));
        p2.add(new Phoneme(Arpabet.IH, 0));
        p2.add(new Phoneme(Arpabet.T, -1));
        assertFalse(p1.rhymesWith(p2));

        // change the consonant, so we are looking at "cat" and "bad", they should not rhyme
        p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.B, -1));
        p2.add(new Phoneme(Arpabet.AE, 0));
        p2.add(new Phoneme(Arpabet.D, -1));
        assertFalse(p1.rhymesWith(p2));
    }

    /**
     * Now we are going to lengthen one of the two words, so we will have different indices for
     * the final stressed vowel. In this case, we'll try "cat" and "splat" - there will still be
     * only one vowel.
     */
    @Test
    @Order(40)
    void  testRhymesWith_DifferentLengths() {
        IPronunciation p1 = new Pronunciation();
        // "cat" and "splat"
        p1.add(new Phoneme(Arpabet.K, -1));
        p1.add(new Phoneme(Arpabet.AE, 0));
        p1.add(new Phoneme(Arpabet.T, -1));
        IPronunciation p2 = new Pronunciation();

        p2.add(new Phoneme(Arpabet.S, -1));
        p2.add(new Phoneme(Arpabet.P, -1));
        p2.add(new Phoneme(Arpabet.L, -1));
        p2.add(new Phoneme(Arpabet.AE, 0));
        p2.add(new Phoneme(Arpabet.T, -1));
        // they should rhyme
        assertTrue(p1.rhymesWith(p2));

        // "cat" and "split" do not
        p2.add(new Phoneme(Arpabet.S, -1));
        p2.add(new Phoneme(Arpabet.P, -1));
        p2.add(new Phoneme(Arpabet.L, -1));
        p2.add(new Phoneme(Arpabet.IH, 0));
        p2.add(new Phoneme(Arpabet.T, -1));
        assertFalse(p1.rhymesWith(p2));

    }

    /**
     * Now we will test "cat" and "non-fat" which should rhyme.
     */
    @Test
    @Order(50)
    void  testRhymesWith_CatAndNonFatRhyme(){
        IPronunciation p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.K, -1));
        p1.add(new Phoneme(Arpabet.AE, 0));
        p1.add(new Phoneme(Arpabet.T, -1));
        IPronunciation p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.N, -1));
        p2.add(new Phoneme(Arpabet.AA, 0));
        p2.add(new Phoneme(Arpabet.N, -1));
        p2.add(new Phoneme(Arpabet.F, -1));
        p2.add(new Phoneme(Arpabet.AE, 1));   // stress here
        p2.add(new Phoneme(Arpabet.T, -1));
        assertTrue(p1.rhymesWith(p2));
    }

    /**
     * Test "cat" and "combat" - these shouldn't rhyme, because the "o" is stressed in "combat". That means
     * that everything after the "o" in "combat" should be considered in the rhyme test.
     */
    @Test
    @Order(60)
    void  testRhymesWith_CatAndCombatDontRhyme(){
        IPronunciation p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.K, -1));
        p1.add(new Phoneme(Arpabet.AE, 0));
        p1.add(new Phoneme(Arpabet.T, -1));
        IPronunciation p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.K, -1));
        p2.add(new Phoneme(Arpabet.AH, 1));   // stress here
        p2.add(new Phoneme(Arpabet.M, -1));
        p2.add(new Phoneme(Arpabet.B, -1));
        p2.add(new Phoneme(Arpabet.AE, 0));
        p2.add(new Phoneme(Arpabet.T, -1));
        assertFalse(p1.rhymesWith(p2));
    }

    /**
     * Test the case where either word has no vowels, so they can't rhyme.
     */
    @Test
    @Order(65)
    void testRhymesWith_NoVowels(){
        // neither word
        IPronunciation p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.K, -1));
        p1.add(new Phoneme(Arpabet.T, -1));
        IPronunciation p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.K, -1));
        p2.add(new Phoneme(Arpabet.M, -1));
        assertFalse(p1.rhymesWith(p2));

        // word 1 has a vowel
        p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.K, -1));
        p1.add(new Phoneme(Arpabet.AE, 0));
        p1.add(new Phoneme(Arpabet.T, -1));
        p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.K, -1));
        p2.add(new Phoneme(Arpabet.M, -1));
        assertFalse(p1.rhymesWith(p2));

        // word 2 has a vowel
        p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.K, -1));
        p1.add(new Phoneme(Arpabet.T, -1));
        p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.K, -1));
        p2.add(new Phoneme(Arpabet.AE, 0));
        p2.add(new Phoneme(Arpabet.T, -1));
        assertFalse(p1.rhymesWith(p2));
    }

    /**
     * Test the case where either word has no phonemes at all
     */
    @Test
    @Order(67)
    void testRhymesWith_ZeroLength(){
        // neither word
        IPronunciation p1 = new Pronunciation();
        IPronunciation p2 = new Pronunciation();
        assertFalse(p1.rhymesWith(p2));

        // word 1 exists and has a vowel
        p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.K, -1));
        p1.add(new Phoneme(Arpabet.AE, 0));
        p1.add(new Phoneme(Arpabet.T, -1));
        p2 = new Pronunciation();
        assertFalse(p1.rhymesWith(p2));

        // word 2 exists and has a vowel
        p1 = new Pronunciation();
        p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.K, -1));
        p2.add(new Phoneme(Arpabet.AE, 0));
        p2.add(new Phoneme(Arpabet.T, -1));
        assertFalse(p1.rhymesWith(p2));

        // word 1 exists and has no vowel
        p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.K, -1));
        p1.add(new Phoneme(Arpabet.T, -1));
        p2 = new Pronunciation();
        assertFalse(p1.rhymesWith(p2));

        // word 2 exists and has no vowel
        p1 = new Pronunciation();
        p2 = new Pronunciation();
        p2.add(new Phoneme(Arpabet.K, -1));
        p2.add(new Phoneme(Arpabet.T, -1));
        assertFalse(p1.rhymesWith(p2));
    }


    //
    // The second part of this file tests Dictionary.getRhymes. A lot of this needs to load the dictionary, so we do
    // that on demand into a static member if it's not loaded already. That means the previous tests should still run.
    //
    private static IDictionary dictionary = null;

    /**
     * Helper static method that loads the dictionary once, the first time we need it. After that
     * it just returns the dictionary we loaded before.
     * @return the dictionary, filled with words from the CMU dictionary.
     */
    private static IDictionary getDictionary(){
        if(dictionary == null){
            dictionary = new Dictionary();
            dictionary.loadDictionary(DictionaryTests.CMU_DICT);
            if(dictionary.getWordCount() == 0){
                dictionary = null; // so we get this exception for subsequent tests
                throw new IllegalStateException("Dictionary not loaded - perhaps Dictionary isn't yet implemented?");
            }
            // if either of these lines fail, then the dictionary hasn't been loaded correctly.
            // If they fail but testLoadDictionary in DictionaryTests passes, something very weird is happening.
            if(dictionary.getWordCount()!=126046)
                throw new IllegalStateException("Dictionary not loaded correctly - expected 126046 words, got "+dictionary.getWordCount());
            if(dictionary.getPronunciationCount()!=135155)
                throw new IllegalStateException("Dictionary not loaded correctly - expected 135155 pronunciations, got "+dictionary.getPronunciationCount());

        }
        return dictionary;
    }


    /**
     * Smoke test - just check the basic case that a word's rhymes contain itself.
     */
    @Test
    @Order(100)
    void testGetRhymes_OrangeOnlyRhymesWithItself(){
        Set<String> rhymes = getDictionary().getRhymes("orange");
        assertTrue(rhymes.contains("orange"));
        assertEquals(1, rhymes.size()); // orange, famously, only rhymes with itself.
    }

    /**
     * Test that there are 77 rhymes for "cat" including "cat" itself and "bat". But also "combat" because
     * there is a second pronunciation with the stress on the final syllable (com-BAT).
     * Also test that "inmarsat" is a rhyme, but I really wouldn't pronounce it like that. It's in the
     * file as IH2 N M AA2 R S AE1 T, with primary stress on the "-at", but I'd personally say "IN-mar-sat."
     * There are a lot of questionable stresses in the CMU dictionary.
     */
    @Test
    @Order(110)
    void testGetRhymes_Cat(){
        Set<String> rhymes = getDictionary().getRhymes("cat");
        assertTrue(rhymes.contains("bat"));
        assertTrue(rhymes.contains("cat"));
        assertTrue(rhymes.contains("combat"));
        assertEquals(77, rhymes.size());
    }

    @Test
    @Order(120)
    void testGetRhymes_Romantic(){
        Set<String> rhymes = getDictionary().getRhymes("romantic");
        assertTrue(rhymes.contains("antic"));
        assertTrue(rhymes.contains("atlantic"));
        assertTrue(rhymes.contains("pedantic"));
        assertTrue(rhymes.contains("semantic"));
        assertTrue(rhymes.contains("frantic"));
        assertTrue(rhymes.contains("sycophantic"));
        assertFalse(rhymes.contains("titanic")); // really no, but nearly! Ends in "AE1 N IH0 K"  not "AE1 N T IH0 K"
        assertEquals(11, rhymes.size());
    }

    /**
     * Now for a test where a word has two pronunciations, so we need to get
     * the rhymes for both of them. Famously, "Tomato" has two pronunciations
     * ("Let's Call the Whole Thing Off", George and Ira Gershwin) - "to-MAY-to" and "to-MAH-to".
     */

    @Test
    @Order(130)
    void testGetRhymes_Tomato(){
        Set<String> rhymes = getDictionary().getRhymes("tomato");
        assertTrue(rhymes.contains("potato"));  // -AY-to
        assertTrue(rhymes.contains("soweto"));  // -AY-to
        assertTrue(rhymes.contains("renato"));  // -AH-to
        assertTrue(rhymes.contains("staccato")); // -AH-to
        assertEquals(98, rhymes.size());
    }

    /**
     * "Ejects" has two pronunciations - one in which the T is pronounced, and another
     * where it is silent ("ejecks").
     */
    @Test
    @Order(140)
    void testGetRhymes_Ejects() {
        Set<String> rhymes = getDictionary().getRhymes("ejects");
        assertTrue(rhymes.contains("affects")); // -EH-kts
        assertTrue(rhymes.contains("effects")); // -EH-kts
        assertTrue(rhymes.contains("rejects")); // -EH-kts
        assertTrue(rhymes.contains("sects")); // -EH-kts
        assertTrue(rhymes.contains("collects")); // -EH-kts

        assertTrue(rhymes.contains("triplex")); // -EH-cks
        assertTrue(rhymes.contains("rex")); // -EH-cks
        assertTrue(rhymes.contains("checks")); // -EH-cks
        assertTrue(rhymes.contains("necks")); // -EH-cks
        assertTrue(rhymes.contains("next")); // -EH-cks (an alternative pronunciation that sounds like "necks")
        assertEquals(95, rhymes.size());
    }

    /**
     * This tests only one valid rhyme of "ejects", so should rhyme with fewer things
     */
    @Test
    @Order(150)
    void testGetRhymes_Necks() {
        Set<String> rhymes = getDictionary().getRhymes("necks");
        assertTrue(rhymes.contains("rex"));
        assertTrue(rhymes.contains("ejects")); // this has a -ex pronunciation (see above), so it does rhyme!
        assertFalse(rhymes.contains("collects")); // this (according to the file) doesn't, so it doesn't rhyme.
        assertEquals(63, rhymes.size());
    }

}
