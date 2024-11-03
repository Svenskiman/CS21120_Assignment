package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.IDictionary;
import uk.ac.aber.cs21120.rhymes.interfaces.IWord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Dictionary implements IDictionary {

    //Creates a hashmap with the words spelling as key and the word object as value
    HashMap<String, IWord> wordDict = new HashMap<>();

    /**
     * Getter method for word objects from the dictionary
     * @param word the spelling of the word
     * @return the word object, or null if the word is not in the dictionary
     */
    @Override
    public IWord getWord(String word) {
        return wordDict.getOrDefault(word, null);
    }

    /**
     * Method for adding words to the dictionary
     * @param word the word to add
     * @throws IllegalArgumentException if the word is already in the dictionary
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
     * Method for returning the total number if pronunciations in the dictionary
     * DOCUMENTATION -- MENTION CONSIDERING FOR-EACH LOOP
     * https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
     * @return the number of pronunciations
     */
    @Override
    public int getPronunciationCount() {
        int pronunciationCount = 0;
        /*
          Iterates through each word object in the dictionary and gets its number
          of pronunciations, totalling them up .
         */
        for (IWord word : wordDict.values()) {
            pronunciationCount += word.getPronunciations().size();
        }
        return pronunciationCount;
    }

    @Override
    public void parseDictionaryLine(String line) {
//        String[] splitLine = new String[]{line};
        String splitComment = line;
        String splitWord;
        String splitPronunciation;

        /*
          Remove line comments and the singular whitespace before it
          https://stackoverflow.com/questions/4419000/regex-match-everything-after-question-mark
         */
        if (line.contains("#")) {
            splitComment = Arrays.toString(line.split(".#(.*)"));
            System.out.println(splitComment);
        }

        //Split word and pronunciation
//        splitWord = Arrays.toString(splitComment.split())
        //https://stackoverflow.com/questions/1400431/regular-expression-match-any-word-until-first-space
        splitPronunciation = Arrays.toString(splitComment.split("^\\S*."));
        System.out.println(splitPronunciation);






    }

    @Override
    public void loadDictionary(String fileName) {

    }

    @Override
    public Set<String> getRhymes(String word) {
//        return Set.of();
        return null;
    }
}
