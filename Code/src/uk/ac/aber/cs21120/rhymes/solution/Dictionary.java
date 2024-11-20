package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IDictionary;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import uk.ac.aber.cs21120.rhymes.interfaces.IWord;

import java.io.*;
import java.util.*;


/**
 * This class represents a dictionary of words from the CMU pronouncing
 * dictionary, and can get all the words that rhyme with a given word.
 */
public class Dictionary implements IDictionary {

    /*
      Creates a hashmap with the words spelling as key and the word object as value.
      By using CTRL+F in the cmudict.dict file and looking for '(' there are 9109 lines
      with this character. This means that there are 9109 alternate pronunciations in the file.
      Therefore, by subtracting this from 135,155 (the total number of lines of text in the file),
      we get the number of words we need to store (126,046). If we then increment this by 1, and set
      our loadFactor to 1 (100%), we will never have to resize our hashmap when adding data to it,
      meaning we won't ever have to copy data over from one HashMap to another, improving the efficiency
      of our algorithm.
      Sources used:
      https://docs.oracle.com/javase/8/docs/api/?java/util/HashMap.html
      https://www.pixelstech.net/article/1588467060-When-will-resizing-be-triggered-in-Java-HashMap
    */
    HashMap<String, IWord> wordDict = new HashMap<>(126047, 1);

    /**
     * Getter method for word objects from the dictionary.
     * @param word the spelling of the word as a string.
     * @return the word object, or null if the word is not in the dictionary.
     */
    @Override
    public IWord getWord(String word) {
        return wordDict.getOrDefault(word, null);
    }

    /**
     * Method for adding word objects to our dictionary (wordDict).
     * @param word the word object to add.
     * @throws IllegalArgumentException if the word is already in the dictionary.
     */
    @Override
    public void addWord(IWord word) throws IllegalArgumentException {
        if (wordDict.containsKey(word.getWord())) {
            throw new IllegalArgumentException("Provided word is already in the dict");
        }
        wordDict.put(word.getWord(), word);
    }

    /**
     * Getter method for the number of words in the dictionary
     * @return the number of words
     */
    @Override
    public int getWordCount() {
        return wordDict.size();
    }

    /**
     * Method for returning the total number of pronunciations in the dictionary.
     * @return the number of pronunciations.
     */
    @Override
    public int getPronunciationCount() {
        int pronunciationCount = 0;
        /*
          Iterates through each word object in the dictionary and gets its number
          of pronunciations, totalling them up.
        */
        for (IWord word : wordDict.values()) {
            pronunciationCount += word.getPronunciations().size();
        }
        return pronunciationCount;
    }

    /**
     * This method parses a line from the cmudict into our own. It does this by removing the comments, and then
     * splitting what's left into word and pronunciation strings. The pronunciation is then broken down into
     * ARPABET enums, which are then used to create a pronunciation object. If the word is not currently
     * in our dictionary, a word object is created and assigned the constructed pronunciation. If the word is
     * already in our dictionary, we add the constructed pronunciation to its list of pronunciations.
     * @param line is the string we are parsing from the cmudict
     */
    @Override
    public void parseDictionaryLine(String line) {
        //Arrays to store the results of string splitting
        String[] splitComment = new String[]{line};
        String[] splitWord;
        String[] splitPronunciation;
        String[] splitPhoneme;

        /*
          Removes line comments and the singular whitespace before it.
          Regex taken and then altered from:
          https://stackoverflow.com/questions/4419000/regex-match-everything-after-question-mark
        */
        if (line.contains("#")) {
            splitComment = line.split(".#(.*)");
        }

        //Split the pronunciation from the given string
        splitPronunciation = splitComment[0].split("(^\\S*.)");

        //Split pronunciation into its phonemes, uses splitPronunciation index 1 as index 0 is ""
        splitPhoneme = splitPronunciation[1].split("\\s", -1);

        //Splits the word from the given string
        splitWord = splitComment[0].split("\\s(.*)");

        /*
          Breaks down all the phonemes into ARPABET enums and their stress values,
          and then uses these to construct a pronunciation object.
        */
        IPronunciation p = new Pronunciation();
        for (String phoneme : splitPhoneme) {
            //Default stress value of -1
            int stress = -1;
            int sizeOfPhoneme = phoneme.length();
            char lastCharacter = phoneme.charAt(sizeOfPhoneme - 1);

            //Checks if the last character in the phoneme is a stress integer
            if (Character.isDigit(lastCharacter)) {
                //Converts stress character into correct stress integer by subtracting the ASCII value of 0
                stress = lastCharacter - '0';
                String[] arpabet = phoneme.split("[012]");
                p.add(new Phoneme(Arpabet.valueOf(arpabet[0]), stress));
            }
            else {
                p.add(new Phoneme(Arpabet.valueOf(phoneme), stress));
            }
        }

        /*
          The following code checks if the parsed word is actually
          just a different pronunciation. If it is, the new pronunciation is added to the
          existing word. If it isn't, a new word object is created and it, alongside its
          pronunciation, are added to the dictionary.
        */
        int sizeOfWord = splitWord[0].length();
        final int subtractForDigitsIndex = 2;

        /*
          If a word is lest than 4 characters, it cannot be an alternate pronunciation due to
          alternate pronunciations are formatted in the cmudict as 'word(p)'.
        */
        if (sizeOfWord < 4) {
            IWord wordObject = new Word(splitWord[0]);
            wordObject.addPronunciation(p);
            addWord(wordObject);
        }
        else {
            char locationOfPronunciationDigit = splitWord[0].charAt(sizeOfWord - subtractForDigitsIndex);

            /*
              Creates and adds a new word if the second to last character is not a digit.
              The OR clause in this if statement and splitting (1) is only needed to pass dictionary tests
              110 and 120, as the cmudict does not have data formatted this way.
            */
            if ((!Character.isDigit(locationOfPronunciationDigit)) || splitWord[0].charAt(sizeOfWord - subtractForDigitsIndex) == '1') {
                splitWord = splitWord[0].split("[(1)]"); //Only needed to pass some of the tests (110, 120)
                IWord wordObject = new Word(splitWord[0]);
                wordObject.addPronunciation(p);
                addWord(wordObject);
            }

            //Gets the existing word from the dictionary and adds its other pronunciation.
            else {
            /*
              Having checked the cmudict with ctrl+f, the most pronunciations a word has is 4, and the
              first occurrence of a word is not signified with (1). This means that the regex pattern
              only has to include numbers 2, 3 and 4.
              IF THE CMU FILE IS CHANGED IN THE FUTURE SO SOME WORDS HAVE 5 OR MORE PRONUNCIATIONS,
              THIS MUST BE CHANGED
            */
                splitWord = splitWord[0].split("[(234)]");
                IWord wordToAddPronunciationTo = getWord(splitWord[0]);
                wordToAddPronunciationTo.addPronunciation(p);
            }
        }
    }

    /**
     * Loads the CMU dictionary from a file using a buffered reader and
     * parses each line of data into the parseDictionaryLine method.
     * @param fileName the name and path of the CMU dictionary file.
     */
    @Override
    public void loadDictionary(String fileName) {
        String line;
        try {
            BufferedReader cmuDictReader = new BufferedReader(new FileReader(fileName));
            while ((line = cmuDictReader.readLine()) != null) {
                parseDictionaryLine(line);
            }
        }
        catch (IOException e) {
            System.out.println("Dictionary file not found");
        }
    }

    /**
     * Gets all the rhymes for all pronunciations of a provided word.
     * @param word the word to get rhymes for.
     * @return a set of words that rhyme with the provided word.
     * @throws IllegalArgumentException if the provided word is not in the dictionary,
     * or is null.
     */
    @Override
    public Set<String> getRhymes(String word) throws IllegalArgumentException {
        //Checks that provided word is valid
        if (word == null || wordDict.get(word) == null) {
            throw new IllegalArgumentException("Provided word is null or does not exist in the dictionary");
        }
        Set<String> rhymes = new HashSet<>();
        //Get the pronunciations of the passed string
        IWord givenWord = wordDict.get(word);
        Set<IPronunciation> givenPronunciations = givenWord.getPronunciations();

        //Loops through each word in the dictionary. Code adjusted from:
        //https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
        for (Map.Entry<String, IWord> entry : wordDict.entrySet()) {
            String key = entry.getKey();
            IWord value = entry.getValue();
            Set<IPronunciation> valuesPronunciations = value.getPronunciations();

            /*
              For each pronunciation of the provided word/string - loops at most 4 times,
              but is most likely to be 1.
            */
            for (IPronunciation pronunciation : givenPronunciations) {

                //For each pronunciation of the dictionary's word - also loops at most 4 times
                for (IPronunciation dictWordPronunciation : valuesPronunciations) {

                    /*
                      If a pronunciation rhymes with the given pronunciation, add it to the set and
                      then break the loop, moving onto the next pronunciation.
                    */
                    if (pronunciation.rhymesWith(dictWordPronunciation)) {
                        rhymes.add(key);
                        break;
                    }
                }
            }
        }
        return rhymes;
    }
}
