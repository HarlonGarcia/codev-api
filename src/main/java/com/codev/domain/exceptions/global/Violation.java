package com.codev.domain.exceptions.global;

import lombok.Data;

@Data
public class Violation {

    private String field;
    private String message;

    public Violation() {
    }

    public Violation(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
