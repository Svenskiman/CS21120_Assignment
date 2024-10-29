package uk.ac.aber.cs21120.rhymes.interfaces;

/**
 * This class represents the ARPABET phonemes used in the CMU Pronouncing Dictionary.
 * The ARPABET is a phonetic transcription system used to represent the pronunciation of words in English.
 *
 * Each phoneme is given with an example word with the phoneme in brackets - for example, the phoneme
 * "AA" is given with the word "f(a)ther", indicating that the AA represents the "a" sound in "father".
 *
 * The ARPABET phonemes are divided into two categories: vowels and consonants. A method "isVowel" is
 * provided to determine if a phoneme is a vowel or not.
 *
 */
public enum Arpabet {
        AA, // "f(a)ther"
        AE, // "b(a)t"
        AH, // "b(u)tt"
        AO, // "c(au)ght", "st(o)ry"
        AW, // "(ou)t"
        AX, // "comm(a)" (unstressed)
        // no AXR
        AY, // "b(i)te"
        EH, // "b(e)t"
        ER, // "b(i)rd"
        EY, // "b(ai)t"
        IH, // "b(i)t"
        IX, // "ros(e)s" (unstressed)
        IY, // "b(ee)t"
        OW, // "b(oa)t", "c(o)ne"
        OY, // "b(oy)"
        UH, // "b(oo)k"
        UW, // "b(oo)t"
        // no UX

        B,  // "(b)at"
        CH, // "(ch)in"
        D,  // "(d)og"
        DH, // "(th)is"
        F,  // "(f)ish"
        G,  // "(g)o"
        // no H or J!
        HH, // "(h)at"
        JH, // "(j)am"
        K,  // "(c)at"
        // no DX
        L,  // "(l)ight"
        M,  // "(m)ap"
        N,  // "(n)ap"
        NG, // "so(ng)"
        // no NX
        P,  // "(p)at"
        // no Q
        R,  // "(r)ed"
        S,  // "(s)ee"
        SH, // "(sh)oe"
        T,  // "(t)ap"
        TH, // "(th)ink"
        V,  // "(v)an"
        W,  // "(w)et"
        Y,  // "(y)es"
        Z,  // "(z)oo"
        ZH;  // "mea(s)ure"

        /**
         * Returns true if the phoneme is a vowel, false otherwise.
         * @return true if the phoneme is a vowel, false otherwise
         */
        public boolean isVowel() {
            return switch (this) {
                case AA, AE, AH, AO, AW, AX, AY, EH, ER, EY, IH, IX, IY, OW, OY, UH, UW -> true;
                default -> false;
            };
        }
}
