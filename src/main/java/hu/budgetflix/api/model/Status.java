package hu.budgetflix.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    DONE,
    ERROR,
    PROCESS;

    public static boolean isDone( Status status) {
        if ( status == null) {
            return false;
        }

        return DONE == status;
    }

    @JsonCreator
    public static Status from(String value) {
        return Status.valueOf(value.toUpperCase());
    }
}