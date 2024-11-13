package uk.ac.aber.cs21120.rhymes.tests;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import uk.ac.aber.cs21120.rhymes.solution.Phoneme;
import uk.ac.aber.cs21120.rhymes.solution.Pronunciation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtraUnitTests {

    @Test
    void testFindFinalStressedVowel_SecondaryAndUnstressed() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.EY, 2));
        p.add(new Phoneme(Arpabet.AH, 0));
        assertEquals(0, p.findFinalStressedVowelIndex());
    }
}
