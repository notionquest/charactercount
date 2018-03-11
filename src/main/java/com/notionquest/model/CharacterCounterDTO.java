package com.notionquest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CharacterCounterDTO implements Serializable {

    private static final long serialVersionUID = -6219097833791226597L;

    private Long alphabetCount;
    private List<Integer> stringNames;

    public CharacterCounterDTO(Long alphabetCount, Integer stringName) {
        this.alphabetCount = alphabetCount;
        addStringNames(stringName);
    }

    public Long getAlphabetCount() {
        return alphabetCount;
    }

    public void setAlphabetCount(Long alphabetCount) {
        this.alphabetCount = alphabetCount;
    }

    public List<Integer> getStringNames() {
        return stringNames;
    }

    public void setStringNames(List<Integer> stringNames) {
        this.stringNames = stringNames;
    }

    public void addStringNames(Integer stringName) {
        if (this.stringNames != null) {
            this.stringNames.add(stringName);
        } else {
            this.stringNames = new ArrayList<>();
            this.stringNames.add(stringName);
        }
    }

    public void replaceStringNames(Integer stringName) {
        this.stringNames = new ArrayList<>();
        this.stringNames.add(stringName);
    }

    @Override
    public String toString() {
        return "CharacterCounterDTO{" +
                "alphabetCount=" + alphabetCount +
                ", stringNames=" + stringNames +
                '}';
    }
}
