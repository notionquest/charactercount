package com.notionquest.service;

import com.notionquest.model.CharacterCounterDTO;
import com.notionquest.util.Constants;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service("simpleFormattingService")
public class SimpleFormattingService implements FormattingStrategy {

    @Override
    public String format(Map<Character, CharacterCounterDTO> alphabetCountMap, Integer numberOfStrings) {

        Objects.requireNonNull(alphabetCountMap, Constants.ALPHABET_MAP_CANNOT_BE_NULL);
        Objects.requireNonNull(numberOfStrings, Constants.NUMBER_OF_STRS_CANNOT_BE_NULL);

        StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<Character, CharacterCounterDTO>> characterConsumer = new Consumer<Map.Entry<Character, CharacterCounterDTO>>() {
            @Override
            public void accept(Map.Entry<Character, CharacterCounterDTO> characterCharacterCounterDTOEntry) {
                if (sb.toString().length() > 0) {
                    sb.append(Constants.FORWARD_SLASH);
                }
                if (characterCharacterCounterDTOEntry.getValue().getStringNames().size() == 1) {
                    sb.append(characterCharacterCounterDTOEntry.getValue().getStringNames().stream().findFirst().get());
                } else if (characterCharacterCounterDTOEntry.getValue().getStringNames().size() > 1
                        && numberOfStrings.equals(characterCharacterCounterDTOEntry.getValue().getStringNames().size())) {
                    sb.append(Constants.EQUALS);
                } else {
                    sb.append(characterCharacterCounterDTOEntry.getValue()
                            .getStringNames().stream()
                            .map(e -> e.toString())
                            .collect(Collectors.joining(Constants.COMMA)));

                }
                sb.append(Constants.COLON);
                sb.append(new String(new char[characterCharacterCounterDTOEntry.getValue().getAlphabetCount().intValue()]).replace("\0", characterCharacterCounterDTOEntry.getKey().toString()));
            }
        };

        alphabetCountMap.entrySet().stream().forEach(characterConsumer);

        return sb.toString();
    }
}

