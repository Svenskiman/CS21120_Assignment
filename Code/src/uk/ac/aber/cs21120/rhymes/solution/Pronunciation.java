package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;

import java.util.ArrayList;
import java.util.List;

/**
 * This class constructs pronunciation objects which represent a word in the
 * CMU pronouncing dictionary. Each pronunciation is made from a list of phonemes,
 * which themselves, are made up of ARPABET enums and optional stress integers.
 */
public class Pronunciation implements IPronunciation {

    //Create an empty list of phonemes
    List<IPhoneme> listOfPhonemes = new ArrayList<>();

    /**
     * Adds a phoneme object to the end of the list.
     * @param phoneme the phoneme to add.
     * @throws IllegalArgumentException if the phoneme object is null.
     */
    @Override
    public void add(IPhoneme phoneme) throws IllegalArgumentException {
        if (phoneme == null) {
            throw new IllegalArgumentException("Provided phoneme is null");
        }
        else {
            listOfPhonemes.add(phoneme);
        }
    }

    /**
     * Getter method for the list of phonemes.
     * @return the list of phonemes that make up the pronunciation.
     */
    @Override
    public List<IPhoneme> getPhonemes() {
        return listOfPhonemes;
    }

    /**
     * Returns the index of the final vowel in the list, with stressed vowels being
     * prioritised, then secondary, then unstressed. If no vowel is found, -1 is
     * returned.
     * @return the index of the final stressed vowel in the pronunciation or -1
     */
    @Override
    public int findFinalStressedVowelIndex() {
        IPhoneme p;
        //Variables to retain index of vowels with secondary or no stress
        int indexUnstressed = -1;
        int indexStressed_2 = -1;

        /*
          Iterate through the list from right to left, so we look at the last phonemes first.
          This means that our best case is Ω(1), which is when the last phoneme has
          as stress of 1.
        */
        for (int i = listOfPhonemes.size() -1 ; i >= 0 ; i--) {
            p = getPhonemes().get(i);
            int stress = p.getStress();
            //Loop breaks as soon as a vowel with stress of 1 is found
            if (stress == 1) {
                return i;
            }
            //Store secondary or no stress vowels index if it has not already been updated
            else if (stress == 2 && indexStressed_2 == -1) {
                indexStressed_2 = i;
            }
            else if (stress == 0 && indexUnstressed == -1) {
                indexUnstressed = i;
            }
        }

        /*
          If no primary stress is found, but pronunciation contains secondary and unstressed vowels,
          prioritise and return secondary stress index.
        */
        if (indexStressed_2 != -1 && indexUnstressed != -1) {
            return indexStressed_2;
        }
        //Return unstressed index or -1
        else {
            return Math.max(indexUnstressed, indexStressed_2);
        }
    }

    /**
     * Returns true if this pronunciation rhymes with the other pronunciation.
     * This is true if the phonemes are the same from the final stressed vowel onwards.
     * @param other the other pronunciation to compare with.
     * @return true if this pronunciation rhymes with the other pronunciation.
     * @throws IllegalArgumentException if the other pronunciation is null.
     */
    @Override
    public boolean rhymesWith(IPronunciation other) throws IllegalArgumentException {
        //Throws exception if other is null
        if (other == null) {
            throw new IllegalArgumentException("The other pronunciation is null");
        }
        //Check that pronunciations are not empty
        if (other.getPhonemes().isEmpty() || getPhonemes().isEmpty()) {
            return false;
        }
        //Check that pronunciations contain a vowel
        if (other.findFinalStressedVowelIndex() == -1 || findFinalStressedVowelIndex() == -1) {
            return false;
        }

        //Store the last phonemes for this pronunciation
        List<Arpabet> thisPhonemesAfterVowel;
        //Store the last phonemes for the other pronunciation
        List<Arpabet> otherPhonemesAfterVowel;

        /*
          This is the website I followed for creating my lambda expression:
          https://www.thesunflowerlab.com/java-lambda-expression/
        */
        //Anonymous inner class
        @FunctionalInterface
        interface PhonemesAfterLastStressedVowel {
            List<Arpabet> getPhonemesAfterVowel(IPronunciation p);
        }

        /*
          Lambda expression that returns an array list containing the last stressed vowel
          and every phoneme after.
        */
        PhonemesAfterLastStressedVowel phonemesAfterLastStressedVowel = (p) -> {
            List<Arpabet> phonemesAfterVowel = new ArrayList<>();
            int phonemeStressedVowelIndex = p.findFinalStressedVowelIndex();
            int endOfPhonemesList = p.getPhonemes().size() - 1;
            IPhoneme phoneme;
            Arpabet arpa;

            //If the last stressed vowel is not at the end of the pronunciation
            if (phonemeStressedVowelIndex != endOfPhonemesList) {
                //Iterate through phonemes, starting from the last stressed vowel
                for (int i = phonemeStressedVowelIndex; i <= endOfPhonemesList; i++) {
                    phoneme = p.getPhonemes().get(i);
                    arpa = phoneme.getArpabet();
                    phonemesAfterVowel.add(arpa);
                }
            }
            else {
                phonemesAfterVowel.add(p.getPhonemes().get(phonemeStressedVowelIndex).getArpabet());
            }
            return phonemesAfterVowel;
        };

        //Call lambda function for this and other pronunciation
        thisPhonemesAfterVowel = phonemesAfterLastStressedVowel.getPhonemesAfterVowel(this);
        otherPhonemesAfterVowel = phonemesAfterLastStressedVowel.getPhonemesAfterVowel(other);

        //Compare the two pronunciations phonemes
        return thisPhonemesAfterVowel.equals(otherPhonemesAfterVowel);
    }
}
