package com.notionquest.service;

import com.notionquest.model.CharacterCounterDTO;
import com.notionquest.util.Constants;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service("mixSortingService")
public class MixSortingService implements SortingStrategy {

    @Override
    public Map<Character, CharacterCounterDTO> sort(Map<Character, CharacterCounterDTO> alphabetCountMap, Integer numberOfStrings) {
        Objects.requireNonNull(alphabetCountMap, Constants.ALPHABET_MAP_CANNOT_BE_NULL);
        Objects.requireNonNull(numberOfStrings, Constants.NUMBER_OF_STRS_CANNOT_BE_NULL);

        Map<Character, CharacterCounterDTO> sortedAlphabetsMap = new LinkedHashMap<>();
        alphabetCountMap.entrySet().stream()
                .sorted((p1, p2) -> {
                    if (p2.getValue().getAlphabetCount().compareTo(p1.getValue().getAlphabetCount()) == 0) {
                        if (p1.getValue().getStringNames().size() == p2.getValue().getStringNames().size()) {
                            //Size matching, so sort alphabetically
                            return p1.getKey().compareTo(p2.getKey());
                        } else {
                            //Size not matching, so sort by size
                            return Integer.valueOf(p1.getValue().getStringNames().size()).compareTo(Integer.valueOf(p2.getValue().getStringNames().size()));
                        }

                    } else {
                        //Descending by alphabets count
                        return p2.getValue().getAlphabetCount().compareTo(p1.getValue().getAlphabetCount());
                    }

                }).forEachOrdered(x -> sortedAlphabetsMap.put(x.getKey(), x.getValue()));
        return sortedAlphabetsMap;

    }
}
