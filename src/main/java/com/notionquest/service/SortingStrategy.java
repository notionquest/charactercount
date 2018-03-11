package com.notionquest.service;

import com.notionquest.model.CharacterCounterDTO;

import java.util.Map;

public interface SortingStrategy {

    /**
     * Apply the sorting rules
     *
     * @param maxCharCount
     * @param numberOfStrings
     * @return
     */
    Map<Character, CharacterCounterDTO> sort(Map<Character, CharacterCounterDTO> maxCharCount, Integer numberOfStrings);
}
