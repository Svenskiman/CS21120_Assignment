package uk.ac.aber.cs21120.rhymes.tests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import uk.ac.aber.cs21120.rhymes.solution.Phoneme;
import uk.ac.aber.cs21120.rhymes.solution.Pronunciation;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PronunciationTests {
    /**
     * Helper method that takes a pronunciation and generates a string, used here
     * and in DictionaryTests
     */
    public static String pronunciationToString(IPronunciation p) {
        StringBuilder sb = new StringBuilder();
        for (IPhoneme o : p.getPhonemes()) {
            sb.append(o.getArpabet().name());
            if (o.getStress() >= 0) {
                sb.append(o.getStress());
            }
            sb.append(" ");
        }
        return sb.toString().trim(); // remove that trailing space
    }

    /**
     * Just make sure we can construct a Pronunciation object without crashing.
     */
    @Test
    @Order(0)
    void testConstruction() {
        IPronunciation p = new Pronunciation();
    }

    /**
     * Test that we can get the phonemes from a Pronunciation object,
     * and it is not null (I wish Java has - like Kotlin - non-nullable types).
     */
    @Test
    @Order(10)
    void testGetPhonemes() {
        IPronunciation p = new Pronunciation();
        assertNotNull(p.getPhonemes());
    }

    /**
     * Test that the newly created Pronunciation object has no phonemes.
     */
    @Test
    @Order(20)
    void testNoPhonemesInNewPronunciation() {
        IPronunciation p = new Pronunciation();
        assertEquals(0, p.getPhonemes().size());
    }

    /**
     * Test that we can add single a phoneme to a Pronunciation object.
     */
    @Test
    @Order(30)
    void testAddPhoneme() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.AH, 2));

        List<IPhoneme> phonemes = p.getPhonemes();
        assertEquals(1, phonemes.size());
        assertEquals(Arpabet.AH, phonemes.get(0).getArpabet());
        assertEquals(2, phonemes.get(0).getStress());
    }

    /**
     * Test that we cannot add a null phoneme to a Pronunciation object.
     */
    @Test
    @Order(35)
    void testCannotAddNullPhoneme() {
        IPronunciation p = new Pronunciation();
        assertThrows(IllegalArgumentException.class, () -> {
            p.add(null);
        });
    }

    /**
     * Test that we can add more than one phoneme to a Pronunciation object.
     */
    @Test
    @Order(40)
    void testAddMultiplePhonemes() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.L, -1));
        p.add(new Phoneme(Arpabet.AH, 2));
        p.add(new Phoneme(Arpabet.K, -1));
        assertEquals(3, p.getPhonemes().size());
        IPhoneme o = p.getPhonemes().get(0);
        assertEquals(Arpabet.L, o.getArpabet());
        assertEquals(-1, o.getStress());
        o = p.getPhonemes().get(1);
        assertEquals(Arpabet.AH, o.getArpabet());
        assertEquals(2, o.getStress());
        o = p.getPhonemes().get(2);
        assertEquals(Arpabet.K, o.getArpabet());
        assertEquals(-1, o.getStress());
    }

    /**
     * Test we can get the final stressed vowel for
     * a vowel-consonant pronunciation: AK2 K
     */
    @Test
    @Order(50)
    void testFindFinalStressedVowelIndex_VC() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.AH, 2));
        p.add(new Phoneme(Arpabet.K, -1));
        assertEquals(0, p.findFinalStressedVowelIndex());
    }

    /**
     * Test we can get the final stressed vowel for
     * a consonant-vowel pronunciation: K AH1
     */
    @Test
    @Order(51)
    void testFindFinalStressedVowelIndex_CV() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.K, -1));
        p.add(new Phoneme(Arpabet.AH, 1));
        assertEquals(1, p.findFinalStressedVowelIndex());
    }

    /**
     * Test we can get the the final stressed vowel for
     * a vowel-only pronunciation: AH1
     */
    @Test
    @Order(52)
    void testFindFinalStressedVowelIndex_V() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.AH, 1));
        assertEquals(0, p.findFinalStressedVowelIndex());
    }


    /**
     * Test we can get the the final stressed vowel for
     * a vowel-only pronunciation with no stress: AH0
     */
    @Test
    @Order(53)
    void testFindFinalStressedVowelIndex_V_NoStress() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.AH, 0));
        assertEquals(0, p.findFinalStressedVowelIndex());
    }


    /**
     * Test we can get the the final stressed vowel for
     * a consonant-vowel-consonant pronunciation: L AH2 K
     */
    @Test
    @Order(54)
    void testFindFinalStressedVowelIndex_CVC() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.L, -1));
        p.add(new Phoneme(Arpabet.AH, 2));
        p.add(new Phoneme(Arpabet.K, -1));
        assertEquals(1, p.findFinalStressedVowelIndex());
    }

    /**
     * Test we can get the the final stressed vowel for
     * a consonant-vowel-consonant pronunciation with no stress: L AH0 K
     */
    @Test
    @Order(55)
    void testFindFinalStressedVowelIndex_CVC_NoStress() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.L, -1));
        p.add(new Phoneme(Arpabet.AH, 0));
        p.add(new Phoneme(Arpabet.K, -1));
        assertEquals(1, p.findFinalStressedVowelIndex());
    }

    /**
     * No vowels, should return -1. Pronunciation is L K.
     */
    @Test
    @Order(56)
    void testFindFinalStressedVowelIndex_NoVowels() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.L, -1));
        p.add(new Phoneme(Arpabet.K, -1));
        assertEquals(-1, p.findFinalStressedVowelIndex());
    }

    /**
     * Test a more complex CVCVCV pronunciation - middle vowel
     * is the last vowel with any stress: L AH2 D ER1 T IY0
     * (This is not a real word; if it were it would be something
     * like "ladderty")
     */
    @Test
    @Order(57)
    void testFindFinalStressedVowelIndex_CVStressedCVCV() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.L, -1));
        p.add(new Phoneme(Arpabet.AH, 2));
        p.add(new Phoneme(Arpabet.D, -1));
        p.add(new Phoneme(Arpabet.ER, 1)); // stressed!
        p.add(new Phoneme(Arpabet.T, -1));
        p.add(new Phoneme(Arpabet.IY, 0));
        assertEquals(3, p.findFinalStressedVowelIndex());
    }

    /**
     * Test a CVCVC pronunciation - first vowel
     * is stressed.
     */
    @Test
    @Order(57)
    void testFindFinalStressedVowelIndex_CVCVStressedC() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.L, -1));
        p.add(new Phoneme(Arpabet.AH, 1)); // stressed
        p.add(new Phoneme(Arpabet.D, -1));
        p.add(new Phoneme(Arpabet.ER, 0)); // unstressed!
        assertEquals(1, p.findFinalStressedVowelIndex());
    }

    /**
     * A more complicated word: misunderstanding
     * M IH2 S AH0 N D ER0 S T AE1 N D IH0 NG
     * I actually disagree with this pronunciation - I think it should be
     * M IH1 S AH0 N D ER0 S T AE2 N D IH0 NG
     * because the stress is stronger on the "AE" than the first syllable,
     * where (certainly in my accent) the stress is often absent.
     *
     * This will also test the pronunciationToString method, which is used internally
     * and in the DictionaryTests class.
     */
    @Test
    @Order(58)
    void testFindFinalStressedVowelIndex_Misunderstanding() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.M, -1)); // 0
        p.add(new Phoneme(Arpabet.IH, 2)); // 1
        p.add(new Phoneme(Arpabet.S, -1)); // 2
        p.add(new Phoneme(Arpabet.AH, 0)); // 3
        p.add(new Phoneme(Arpabet.N, -1)); // 4
        p.add(new Phoneme(Arpabet.D, -1)); // 5
        p.add(new Phoneme(Arpabet.ER, 0)); // 6
        p.add(new Phoneme(Arpabet.S, -1)); // 7
        p.add(new Phoneme(Arpabet.T, -1)); // 8
        p.add(new Phoneme(Arpabet.AE, 1)); // 9
        p.add(new Phoneme(Arpabet.N, -1)); // 10
        p.add(new Phoneme(Arpabet.D, -1)); // 11
        p.add(new Phoneme(Arpabet.IH, 0)); // 12
        p.add(new Phoneme(Arpabet.NG, -1)); // 13
        assertEquals(9, p.findFinalStressedVowelIndex());

        assertEquals("M IH2 S AH0 N D ER0 S T AE1 N D IH0 NG", pronunciationToString(p));
    }

    /**
     * One more test I couldn't resist.
     */
    @Test
    @Order(66)
    void testFindFinalStressedVowel_CVStressedCV() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.JH, -1));
        p.add(new Phoneme(Arpabet.EH, 1));
        p.add(new Phoneme(Arpabet.D, -1));
        p.add(new Phoneme(Arpabet.AY, 0));
        assertEquals(1, p.findFinalStressedVowelIndex());
    }

    /**
     * Test that we ignore trailing secondary stresses.
     * We only look for the primary stress.
     */
    @Test
    @Order(70)
    void testFindFinalStressedVowel_Workbench() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.W, -1));
        p.add(new Phoneme(Arpabet.ER, 1));    // this is primary stress
        p.add(new Phoneme(Arpabet.K, -1));
        p.add(new Phoneme(Arpabet.B, -1));
        p.add(new Phoneme(Arpabet.EH, 2));   // ignore this secondary stress
        p.add(new Phoneme(Arpabet.N, -1));
        p.add(new Phoneme(Arpabet.CH, -1));
        assertEquals(1, p.findFinalStressedVowelIndex());
    }
}