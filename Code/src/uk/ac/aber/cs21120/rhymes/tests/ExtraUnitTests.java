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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExtraUnitTests {

    //Tests 0 through 5 are to do with the Pronunciation class.
    //Test 6 is to do with rhyming

    /**
     * Test we can get the final stressed vowel for
     * a secondary stressed vowel-only pronunciation: EY2
     */
    @Test
    @Order(0)
    void testFindFinalStressedVowel_OnlySecondaryStress() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.EY, 2));
        assertEquals(0, p.findFinalStressedVowelIndex());
    }

    /**
     * Test no vowels with a pronunciation of length 1
     * Should return -1
     */
    @Test
    @Order(1)
    void testFindFinalStressedVowel_NoVowel_SingularLength() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.L, -1));
        assertEquals(-1, p.findFinalStressedVowelIndex());
    }

    /**
     * Test that we prioritise secondary stress over no stress when no primary
     * stress is present.
     */
    @Test
    @Order(3)
    void testFindFinalStressedVowel_SecondaryAndUnstressed1() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.EY, 2));
        p.add(new Phoneme(Arpabet.AH, 0));
        assertEquals(0, p.findFinalStressedVowelIndex());
    }

    /**
     * Change order, secondary stress should still be prioritised.
     */
    @Test
    @Order(4)
    void testFindFinalStressedVowel_SecondaryAndUnstressed2() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.EY, 0));
        p.add(new Phoneme(Arpabet.AH, 2));
        assertEquals(1, p.findFinalStressedVowelIndex());
    }

    /**
     * Multiple secondary stresses, should return the last 1.
     */
    @Test
    @Order(5)
    void testFindFinalStressedVowel_SecondaryAndUnstressed3() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.AH, 2));
        p.add(new Phoneme(Arpabet.EY, 0));
        p.add(new Phoneme(Arpabet.AH, 2));
        assertEquals(2, p.findFinalStressedVowelIndex());
    }


    //Taken from DictionaryTests
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
     * Tests that while 2 words may rhyme, the words those 2 words rhyme with might be different.
     * Checks that a words alternate pronunciations are considered
     */
    @Test
    @Order(6)
    void testWordsThatRhymeWithAWord_ShouldNotAlwaysRhymeWithEachOther() {
        Set<String> rhymes = getDictionary().getRhymes("associate");
        Set<String> rhymes2 = getDictionary().getRhymes("renegotiate");
        assertTrue(rhymes.contains("renegotiate")); //associate rhymes with renegotiate
        assertTrue(rhymes.contains("dissociate")); //associate rhymes with dissociate
        assertTrue(rhymes2.contains("associate")); //renegotiate rhymes with associate
        assertFalse(rhymes2.contains("dissociate")); //This is the catch - renegotiate doesn't rhyme with dissociate
    }
}

