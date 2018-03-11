package com.notionquest.model;

import java.io.Serializable;
import java.util.List;

public class ExceptionResponse implements Serializable {

    private static final long serialVersionUID = 6443366414444189637L;
    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "errors=" + errors +
                '}';
    }
}
