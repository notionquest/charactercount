package com.notionquest.model;

import java.io.Serializable;

public class MixStringsOutcome implements Serializable {
    private static final long serialVersionUID = 4416918696579423638L;

    private String mixString;

    public MixStringsOutcome() {
    }

    public MixStringsOutcome(String mixString) {
        this.mixString = mixString;
    }

    public String getMixString() {
        return mixString;
    }

    public void setMixString(String mixString) {
        this.mixString = mixString;
    }

    @Override
    public String toString() {
        return "MixStringsOutcome{" +
                "mixString='" + mixString + '\'' +
                '}';
    }
}
