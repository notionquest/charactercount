package com.notionquest.service;

import com.notionquest.model.CharacterCounterDTO;
import com.notionquest.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CharacterCounterService extends BaseCharacterCounter {

    @Autowired
    @Qualifier("mixSortingService")
    private SortingStrategy sortingStrategy;

    @Autowired
    @Qualifier("simpleFormattingService")
    private FormattingStrategy formattingStrategy;

    /**
     * To get the mix for the input strings
     *
     * @param strings
     * @return
     */
    public String getMix(List<String> strings) {
        //Validation
        if (strings == null) {
            throw new IllegalArgumentException(Constants.ERROR_INPUT_STR_NULL);
        }

        if (strings.size() < 2) {
            throw new IllegalArgumentException(Constants.ATLEAST_TWO_STRINGS_PRESENT_IN_INPUT);
        }

        //Filter and get only lower case alphabets
        List<String> lowercaseStrings = getLowercaseAlphabets(strings);

        //Count the alphabets
        Map<Character, CharacterCounterDTO> maxCharCount = null;
        for (int strNumber = 0; strNumber < strings.size(); strNumber++) {
            Map<Character, Long> alphabetCountMap = getCharacterCount(lowercaseStrings.get(strNumber));
            maxCharCount = findMaximumCountForAlphabets(alphabetCountMap, maxCharCount, strNumber + 1);
        }

        //Sort the alphabets count
        Map<Character, CharacterCounterDTO> sortedAlphabetsMap = sortingStrategy.sort(maxCharCount, strings.size());

        //Format into string
        return formattingStrategy.format(sortedAlphabetsMap, strings.size());
    }


}
