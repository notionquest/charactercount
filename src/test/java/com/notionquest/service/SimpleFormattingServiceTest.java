package com.notionquest.service;

import com.notionquest.model.CharacterCounterDTO;
import com.notionquest.util.Constants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SimpleFormattingServiceTest {

    private FormattingStrategy formattingStrategy;

    private Map<Character, CharacterCounterDTO> alphabetCountMap;

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Before
    public void init() {
        formattingStrategy = new SimpleFormattingService();
        alphabetCountMap = new HashMap<>();
    }

    @Test
    public void shouldGetTheFormattedString() {

        alphabetCountMap.put('a', new CharacterCounterDTO(3l, 1));
        alphabetCountMap.put('c', new CharacterCounterDTO(2l, 1));
        alphabetCountMap.put('b', new CharacterCounterDTO(3l, 2));
        alphabetCountMap.get('c').addStringNames(2);
        assertEquals("1:aaa/2:bbb/=:cc",formattingStrategy.format(alphabetCountMap, 2));
    }

    @Test
    public void shouldThrowExceptionWhenMapIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(Constants.ALPHABET_MAP_CANNOT_BE_NULL);
        formattingStrategy.format(null, 2);
    }


    @Test
    public void shouldThrowExceptionWhenNumberOfStringsIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(Constants.NUMBER_OF_STRS_CANNOT_BE_NULL);
        formattingStrategy.format(alphabetCountMap, null);
    }
}
