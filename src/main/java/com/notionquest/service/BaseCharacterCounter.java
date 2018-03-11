package com.notionquest.service;

import com.notionquest.model.CharacterCounterDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseCharacterCounter {

    /**
     * Get the lower case alphabets from String i.e. ignore all other characters
     *
     * @param strings
     * @return
     */
    protected List<String> getLowercaseAlphabets(List<String> strings) {
        List<String> stringsWithLowerCase = new ArrayList<>();
        Consumer<String> consumerNames = new Consumer<String>() {
            public void accept(String name) {
                stringsWithLowerCase.add(name.replaceAll("([^a-z])", ""));
            }
        };
        strings.stream().forEach(consumerNames);

        return stringsWithLowerCase;
    }

    /**
     * Get characters count in the String
     *
     * @param inputString
     * @return
     */
    protected Map<Character, Long> getCharacterCount(String inputString) {
        return inputString.chars().mapToObj(i -> Character.valueOf((char) i)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    }

    /**
     *
     * Compare and form the master alphabets maximum count collection
     *
     * @param charCountMap - Alphabet count of the string
     * @param alphabetMaxCountMap - Master map to keep the max count for each alphabets
     * @param stringSerialNo - Serial number of the input String
     * @return
     */
    protected Map<Character, CharacterCounterDTO> findMaximumCountForAlphabets(Map<Character, Long> charCountMap, Map<Character, CharacterCounterDTO> alphabetMaxCountMap, Integer stringSerialNo) {

        if (alphabetMaxCountMap == null) {
            alphabetMaxCountMap = new HashMap<>();
        }

        Map<Character, Long> filteredCharCountMap = new HashMap<>();
        //Ignore the alphabets where count is 1
        if (charCountMap != null) {
            filteredCharCountMap = charCountMap.entrySet().stream().filter(e -> e.getValue() > 1).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        }

        for (Map.Entry<Character, Long> entry : filteredCharCountMap.entrySet()) {
            if (alphabetMaxCountMap.containsKey(entry.getKey())) {
                CharacterCounterDTO characterCounterDTO = alphabetMaxCountMap.get(entry.getKey());
                //New max value for the alphabet
                if (entry.getValue() > characterCounterDTO.getAlphabetCount()) {
                    characterCounterDTO.setAlphabetCount(entry.getValue());
                    characterCounterDTO.replaceStringNames(stringSerialNo);
                    alphabetMaxCountMap.put(entry.getKey(), characterCounterDTO);
                } else if (entry.getValue().equals(characterCounterDTO.getAlphabetCount())) {
                    //Same max value for the alphabet
                    characterCounterDTO.addStringNames(stringSerialNo);
                }

            } else {
                //Alphabet is not found
                CharacterCounterDTO characterCounterDTO = new CharacterCounterDTO(entry.getValue(), stringSerialNo);
                alphabetMaxCountMap.put(entry.getKey(), characterCounterDTO);
            }

        }

        return alphabetMaxCountMap;
    }

}
