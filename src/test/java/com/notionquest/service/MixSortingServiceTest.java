package com.notionquest.service;

import com.notionquest.model.CharacterCounterDTO;
import com.notionquest.util.Constants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class MixSortingServiceTest {

    private SortingStrategy sortingStrategy;

    private Map<Character, CharacterCounterDTO> alphabetCountMap;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        sortingStrategy = new MixSortingService();
        alphabetCountMap = new HashMap<>();
    }

    @Test
    public void shouldSortTheMapByAlphabetsCount() {

        alphabetCountMap.put('c', new CharacterCounterDTO(2l, 1));
        alphabetCountMap.put('b', new CharacterCounterDTO(3l, 2));
        alphabetCountMap.put('a', new CharacterCounterDTO(3l, 1));
        alphabetCountMap.get('c').addStringNames(2);

        LinkedHashMap<Character, CharacterCounterDTO> sortedAlphabetsMap = (LinkedHashMap) sortingStrategy.sort(alphabetCountMap, 2);

        assertThat(sortedAlphabetsMap.keySet(), contains('a', 'b', 'c'));
    }

    @Test
    public void shouldSortTheMapByAlphabetsCountForThreeStrings() {

        alphabetCountMap.put('c', new CharacterCounterDTO(2l, 1));
        alphabetCountMap.put('b', new CharacterCounterDTO(3l, 2));
        alphabetCountMap.put('a', new CharacterCounterDTO(3l, 1));
        alphabetCountMap.get('c').addStringNames(2);
        alphabetCountMap.put('a', new CharacterCounterDTO(5l, 3));

        LinkedHashMap<Character, CharacterCounterDTO> sortedAlphabetsMap = (LinkedHashMap) sortingStrategy.sort(alphabetCountMap, 2);

        assertThat(sortedAlphabetsMap.keySet(), contains('a', 'b', 'c'));
        assertEquals(new Integer(3), sortedAlphabetsMap.get('a').getStringNames().stream().findFirst().get());
    }

    @Test
    public void shouldSortTheMapByCountAndAlphabetalOrder() {

        alphabetCountMap.put('c', new CharacterCounterDTO(2l, 1));
        alphabetCountMap.put('b', new CharacterCounterDTO(5l, 2));
        alphabetCountMap.put('a', new CharacterCounterDTO(2l, 1));
        alphabetCountMap.get('c').addStringNames(2);

        LinkedHashMap<Character, CharacterCounterDTO> sortedAlphabetsMap = (LinkedHashMap) sortingStrategy.sort(alphabetCountMap, 2);

        assertThat(sortedAlphabetsMap.keySet(), contains('b', 'a', 'c'));
    }

    @Test
    public void shouldThrowExceptionWhenMapIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(Constants.ALPHABET_MAP_CANNOT_BE_NULL);
        sortingStrategy.sort(null, 2);
    }


    @Test
    public void shouldThrowExceptionWhenNumberOfStringsIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(Constants.NUMBER_OF_STRS_CANNOT_BE_NULL);
        sortingStrategy.sort(alphabetCountMap, null);
    }

}
