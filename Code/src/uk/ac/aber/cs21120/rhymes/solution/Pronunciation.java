package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;

import java.util.ArrayList;
import java.util.List;

public class Pronunciation implements IPronunciation {

    //Create an empty list of phonemes
    List<IPhoneme> listOfPhonemes = new ArrayList<>();

    /**
     * Adds a phoneme object to the end of the list
     * @param phoneme the phoneme to add
     * @throws IllegalArgumentException if the phoneme object is null
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
     * Getter method for the list of phonemes
     * @return the list of phonemes that make up the pronunciation
     */
    @Override
    public List<IPhoneme> getPhonemes() {
        return listOfPhonemes;
    }


    /**
     * Returns the index of final vowel in the list, with stressed vowels being
     * prioritised then secondary, then unstressed. If no vowel is found, -1 is
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
          Iterate through the list from right to left, so we look at the last phonemes first,
          means that we will only have to iterate through the whole list in the worst case -
          when the list contains no vowels.
         */

        for (int i = listOfPhonemes.size() -1 ; i >= 0 ; i--) {
            p = getPhonemes().get(i);
            boolean pIsVowel = p.getArpabet().isVowel();
            int stress = p.getStress();

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
          This code executes if no vowel is found to have a stress of 1.
          It returns the index of the vowel that is closest to the end of
          the list by returning the variable with the larger index value.
          If both variables are the same, their default value of -1 is returned
          as that means the list contains no vowels.
        */
        return Math.max(indexUnstressed, indexStressed_2);
    }

    //STUB METHOD, COMPLETE LATER
    @Override
    public boolean rhymesWith(IPronunciation other) {
        return false;
    }
}
