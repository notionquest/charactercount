package com.notionquest.service;

import com.notionquest.model.CharacterCounterDTO;

import java.util.Map;

public interface FormattingStrategy {

    /**
     * Form/Format the output string
     *
     * @param alphabetCountMap
     * @param numberOfStrings
     * @return
     */
    String format(Map<Character, CharacterCounterDTO> alphabetCountMap, Integer numberOfStrings);
}
