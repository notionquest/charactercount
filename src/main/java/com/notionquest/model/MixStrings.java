package com.notionquest.model;

import java.io.Serializable;
import java.util.List;

public class MixStrings implements Serializable {
    private static final long serialVersionUID = 1595432620604662035L;
    private List<String> strings;

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public String toString() {
        return "MixStrings{" +
                "strings=" + strings +
                '}';
    }
}
