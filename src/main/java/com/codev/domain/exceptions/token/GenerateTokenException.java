package com.codev.domain.exceptions.token;

import lombok.Data;

@Data
public class GenerateTokenException extends Exception {

    private String message;

    public GenerateTokenException() {
        this.message = "Token generation failed";
    }

    public GenerateTokenException(String message) {
        this.message = message;
    }

}
