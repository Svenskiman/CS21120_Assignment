package uk.ac.aber.cs21120.rhymes.tests;

import org.junit.jupiter.api.*;
import uk.ac.aber.cs21120.rhymes.interfaces.*;
import uk.ac.aber.cs21120.rhymes.solution.Dictionary;
import uk.ac.aber.cs21120.rhymes.solution.Pronunciation;
import uk.ac.aber.cs21120.rhymes.solution.Word;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DictionaryTests {

    // The location of the CMU dictionary file
    // CHANGE THIS TO THE LOCATION OF YOUR DICTIONARY FILE
    public static final String CMU_DICT = "C:/Users/benha/Documents/IntelliJ Projects/CS21120_Assignment-main/Code/cmudict.dict";
    //c:/users/jim/ideaProjects/rhymes/cmudict.dict

    /**
     * Helper method to get all pronunciations of a word as a list of strings sorted
     * alphabetically (well, according to ASCII order).
     */
    static private List<String> getPronunciations(IWord w) {
        // this takes the pronunciations list, gets a stream from it,
        // puts all the elements of that stream through the pronunciationToString helper method,
        // sorts the result, and then collects it into a list.
        return w.getPronunciations().stream()
                .map(PronunciationTests::pronunciationToString)
                .sorted(String::compareTo).toList();
    }

    /**
     * make sure we can construct a dictionary and that a suitable
     * structure was made (which we can confirm by checking the size of the
     * dictionary).
     */
    @Test
    @Order(0)
    void testConstruction() {
        IDictionary d = new Dictionary();
        assertEquals(0, d.getWordCount());
    }

    /**
     * Test that asking for a word in an empty dictionary
     * returns null.
     */
    @Test
    @Order(5)
    void testGetWordEmpty() {
        IDictionary d = new Dictionary();
        assertNull(d.getWord("hello"));
    }

    /**
     * Test that we can add words to the dictionary and retrieve them,
     * and that the word count is correct.
     */
    @Test
    @Order(10)
    void testAddWord() {
        IDictionary d = new Dictionary();
        d.addWord(new Word("hello"));
        assertEquals(1, d.getWordCount());
        assertEquals(d.getWord("hello").getWord(), "hello");
        d.addWord(new Word("world"));
        assertEquals(2, d.getWordCount());
        assertEquals(d.getWord("world").getWord(), "world");
    }

    /**
     * Test that we can't add the same word twice.
     */

    @Test
    @Order(20)
    void testAddDuplicateWord() {
        IDictionary d = new Dictionary();
        d.addWord(new Word("hello"));
        assertThrows(IllegalArgumentException.class, () -> {
            d.addWord(new Word("hello"));
        });
    }

    /**
     * Test we can't get a word that isn't there
     */
    @Test
    @Order(25)
    void testGetMissingWord() {
        IDictionary d = new Dictionary();
        d.addWord(new Word("hello"));       // hello is there
        assertNull(d.getWord("world"));     // but world isn't!
    }

    /**
     * Test the pronunciation count is correct for words with zero
     * pronunciations!
     */

    @Test
    @Order(30)
    void testPronunciationCount() {
        IDictionary d = new Dictionary();
        assertEquals(0, d.getPronunciationCount());
        d.addWord(new Word("hello"));
        assertEquals(0, d.getPronunciationCount());
        d.addWord(new Word("world"));
        assertEquals(0, d.getPronunciationCount());
    }

    /**
     * Test the pronunciation count is correct for words with one
     * pronunciation
     */

    @Test
    @Order(40)
    void testPronunciationCountOne() {
        IDictionary d = new Dictionary();
        Word w = new Word("hello");
        w.addPronunciation(new Pronunciation());
        d.addWord(w);
        assertEquals(1, d.getPronunciationCount());

        w = new Word("world");
        w.addPronunciation(new Pronunciation());
        d.addWord(w);
        assertEquals(2, d.getPronunciationCount());
    }

    /**
     * Test the pronunciation count is correct for words with multiple
     * pronunciations - we do NOT check that the pronunciations are
     * not equal.
     */
    @Test
    @Order(50)
    void testPronunciationCountMultiple() {
        IDictionary d = new Dictionary();
        Word w = new Word("hello");
        w.addPronunciation(new Pronunciation());
        w.addPronunciation(new Pronunciation());
        d.addWord(w);
        assertEquals(2, d.getPronunciationCount());

        w = new Word("world");
        w.addPronunciation(new Pronunciation());
        w.addPronunciation(new Pronunciation());
        w.addPronunciation(new Pronunciation());
        d.addWord(w);
        assertEquals(5, d.getPronunciationCount());
    }

    /*
     *
     * Below this point a lot of tests will fail if you haven't written the
     * parsing code.
     *
     */

    /**
     * Try to parse the simplest possible dictionary line -
     * a word with a single phoneme which is a consonant (and so has no stress).
     */
    @Test
    @Order(60)
    void testParseDictionaryLineOnePhoneme() {
        IDictionary d = new Dictionary();
        d.parseDictionaryLine("qqq K");
        // if this is wrong, you may not be adding the word to the dictionary correctly
        assertEquals(1, d.getWordCount());
        // if this is wrong, you may not be adding the pronunciation to the word.
        assertEquals(1, d.getPronunciationCount());
        // check the word is in the dictionary
        assertEquals("qqq", d.getWord("qqq").getWord());

        // now get the pronunciation and check it
        IPronunciation p = d.getWord("qqq").getPronunciations().iterator().next();
        List<IPhoneme> pos = p.getPhonemes();
        assertEquals(1, pos.size());
        assertEquals(Arpabet.K, pos.get(0).getArpabet());
        assertEquals(-1, pos.get(0).getStress());
    }

    /**
     * Try to parse a dictionary line with a word with a single phoneme
     * which is a vowel (and so has a stress).
     */
    @Test
    @Order(70)
    void testParseDictionaryLineOneVowel() {
        IDictionary d = new Dictionary();
        d.parseDictionaryLine("eye AY1");
        assertEquals(1, d.getWordCount());
        assertEquals(1, d.getPronunciationCount());
        // check the word is in the dictionary
        assertEquals("eye", d.getWord("eye").getWord());

        // now get the pronunciation and check it
        IPronunciation p = d.getWord("eye").getPronunciations().iterator().next();
        List<IPhoneme> pos = p.getPhonemes();
        assertEquals(1, pos.size());
        assertEquals(Arpabet.AY, pos.get(0).getArpabet());
        assertEquals(1, pos.get(0).getStress());
    }

    /**
     * Now parse a dictionary line with a word with two phonemes.
     */
    @Test
    @Order(80)
    void testParseDictionaryLineTwoPhonemesA() {
        IDictionary d = new Dictionary();
        d.parseDictionaryLine("fie F AY1");
        assertEquals(1, d.getWordCount());
        assertEquals(1, d.getPronunciationCount());
        // check the word is in the dictionary
        assertEquals("fie", d.getWord("fie").getWord());
        // now get the pronunciation and check it
        IPronunciation p = d.getWord("fie").getPronunciations().iterator().next();
        List<IPhoneme> pos = p.getPhonemes();
        assertEquals(2, pos.size());
        assertEquals(Arpabet.F, pos.get(0).getArpabet());
        assertEquals(-1, pos.get(0).getStress());
        assertEquals(Arpabet.AY, pos.get(1).getArpabet());
        assertEquals(1, pos.get(1).getStress());
    }

    /**
     * Just to check, another two-phoneme word. This is also the first
     * one to feature a two-character consonant, SH.
     */
    @Test
    @Order(90)
    void testParseDictionaryLineTwoPhonemesB() {
        IDictionary d = new Dictionary();
        d.parseDictionaryLine("ash AE1 SH");
        assertEquals(1, d.getWordCount());
        assertEquals(1, d.getPronunciationCount());
        // check the word is in the dictionary
        assertEquals("ash", d.getWord("ash").getWord());
        // now get the pronunciation and check it
        IPronunciation p = d.getWord("ash").getPronunciations().iterator().next();
        List<IPhoneme> pos = p.getPhonemes();
        assertEquals(2, pos.size());
        assertEquals(Arpabet.AE, pos.get(0).getArpabet());
        assertEquals(1, pos.get(0).getStress());
        assertEquals(Arpabet.SH, pos.get(1).getArpabet());
        assertEquals(-1, pos.get(1).getStress());
    }

    /**
     * Three phonemes in a word.
     */
    @Test
    @Order(100)
    void testParseDictionaryLineThreePhonemes() {
        IDictionary d = new Dictionary();
        d.parseDictionaryLine("ring R IH1 NG");
        assertEquals(1, d.getWordCount());
        assertEquals(1, d.getPronunciationCount());

        IPronunciation p = d.getWord("ring").getPronunciations().iterator().next();
        List<IPhoneme> pos = p.getPhonemes();
        assertEquals(3, pos.size());
        assertEquals(Arpabet.R, pos.get(0).getArpabet());
        assertEquals(-1, pos.get(0).getStress());
        assertEquals(Arpabet.IH, pos.get(1).getArpabet());
        assertEquals(1, pos.get(1).getStress());
        assertEquals(Arpabet.NG, pos.get(2).getArpabet());
        assertEquals(-1, pos.get(2).getStress());
    }

    /**
     * And more than three phonemes in a word. This one is, frankly, taking the mickey.
     */
    @Test
    @Order(110)
    void testParseDictionaryLineVeryManyPhonemes() {
        IDictionary d = new Dictionary();
        d.parseDictionaryLine("antidisestablishmentarianism AE2 N T AY0 D IH2 S AH0 S T AE2 B L IH0 SH M AH0 N T EH1 R IY0 AH0 N IH2 Z AH0 M");
        assertEquals(1, d.getWordCount());
        assertEquals(1, d.getPronunciationCount());

        IPronunciation p = d.getWord("antidisestablishmentarianism")
                .getPronunciations().iterator().next();
        List<IPhoneme> pos = p.getPhonemes();
        assertEquals(28, pos.size());
        // rather than compare each phoneme by hand, we'll convert to a string using a method
        // in PronunciationTests.
        String s = PronunciationTests.pronunciationToString(p);
        assertEquals("AE2 N T AY0 D IH2 S AH0 S T AE2 B L IH0 SH M AH0 N T EH1 R IY0 AH0 N IH2 Z AH0 M", s);
    }

    /**
     * Test that we can add a word with multiple pronunciations
     */
    @Test
    @Order(120)
    void testParseDictionaryLineMultiplePronunciations() {
        IDictionary d = new Dictionary();
        d.parseDictionaryLine("read(1) R EH1 D");
        d.parseDictionaryLine("read(2) R IY1 D");
        assertEquals(1, d.getWordCount());
        assertEquals(2, d.getPronunciationCount());

        IWord w = d.getWord("read");
        assertEquals("read", w.getWord());
        assertEquals(2, w.getPronunciations().size());
        // the awkwardness here is that we don't know what order the pronunciations will be in
        // because we're using a set. To get round this, we turn both sets into strings and
        // add them to a list. Then we sort the list. Now that they are in a known order, we
        // can check them. I've kept this bit of code as a kind of demo, but generally
        // we can use the private helper method getPronunciations to do this.
        List<String> strings = new ArrayList<>();
        for(IPronunciation p : w.getPronunciations()) {
            strings.add(PronunciationTests.pronunciationToString(p));
        }
        strings.sort(String::compareTo);
        // they will now be in dictionary order.
        assertEquals("R EH1 D", strings.get(0));
        assertEquals("R IY1 D", strings.get(1));
    }

    /**
     * Test that comments are parsed correctly when there is only
     * one pronunciation
     */
    @Test
    @Order(130)
    void testParseDictionaryLineCommentOnePronunciation() {
        IDictionary d = new Dictionary();
        d.parseDictionaryLine("read(1) R EH1 D # this is a comment");
        assertEquals(1, d.getWordCount());
        assertEquals(1, d.getPronunciationCount());

        IWord w = d.getWord("read");
        assertEquals("read", w.getWord());
        assertEquals(1, w.getPronunciations().size());
        IPronunciation p = w.getPronunciations().iterator().next();
        String s = PronunciationTests.pronunciationToString(p);
        assertEquals("R EH1 D", s);
    }

    /**
     * Test that comments are parsed correctly for lines with multiple pronunciations
     */
    @Test
    @Order(140)
    void testParseDictionaryLineCommentMultiplePronunciations() {
        IDictionary d = new Dictionary();
        // seriously, who pronounces "aphid" with a short A? The CMU dict has some very odd entries.
        // I also think the final vowel should be IH0, not AH0. It's "ay-fid", not "aff-ud".
        d.parseDictionaryLine("aphid AE1 F AH0 D # this is a comment");
        d.parseDictionaryLine("aphid(2) EY1 F AH0 D # this is another comment");
        assertEquals(1, d.getWordCount());
        assertEquals(2, d.getPronunciationCount());

        IWord w = d.getWord("aphid");
        assertEquals("aphid", w.getWord());
        assertEquals(2, w.getPronunciations().size());
        List<String> strings = new ArrayList<>();
        for(IPronunciation p : w.getPronunciations()) {
            strings.add(PronunciationTests.pronunciationToString(p));
        }
        strings.sort(String::compareTo);
        assertEquals("AE1 F AH0 D", strings.get(0));
        assertEquals("EY1 F AH0 D", strings.get(1));
    }

    /**
     * Attempt to load the CMU dictionary file and make sure the count of words and pronunciations
     * is good.
     * This will fail if
     * - you haven't written the loadDictionary method correctly
     * - downloaded the cmudict.dict file from https://users.aber.ac.uk/jcf12/downloads/cmudict.dict
     * - changed the location of the file in the CMU_DICT constant at the top of this class
     */
    @Test
    @Order(150)
    void testLoadDictionary() {
        IDictionary d = new Dictionary();
        d.loadDictionary(CMU_DICT);

        assertEquals(126046, d.getWordCount());
        assertEquals(135155, d.getPronunciationCount());
    }

    /**
     * Load the dictionary and test a few random words
     */
    @Test
    @Order(160)
    void testSpecificWordsFromLoadedDictionary() {
        IDictionary d = new Dictionary();
        d.loadDictionary(CMU_DICT);

        // word with one pronunciation
        IWord w = d.getWord("clover");
        assertNotNull(w);
        assertEquals("clover", w.getWord());
        List<String> prons = getPronunciations(w);
        assertEquals("K L OW1 V ER0", prons.get(0));

        // two prons
        w = d.getWord("hello");
        assertNotNull(w);
        assertEquals("hello", w.getWord());
        prons = getPronunciations(w);
        assertEquals("HH AH0 L OW1", prons.get(0));
        assertEquals("HH EH0 L OW1", prons.get(1));

        // four! pronunciations
        w = d.getWord("remembering");
        assertNotNull(w);
        assertEquals("remembering", w.getWord());
        prons = getPronunciations(w);
        assertEquals("R IH0 M EH1 M B ER0 IH0 NG", prons.get(0));
        assertEquals("R IH0 M EH1 M B R IH0 NG", prons.get(1));
        assertEquals("R IY0 M EH1 M B ER0 IH0 NG", prons.get(2));
        assertEquals("R IY0 M EH1 M B R IH0 NG", prons.get(3));
    }
}
