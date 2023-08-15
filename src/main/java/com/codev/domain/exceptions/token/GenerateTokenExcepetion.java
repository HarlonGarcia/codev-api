package com.codev.domain.exceptions.token;

import lombok.Data;

@Data
public class GenerateTokenExcepetion extends Exception {

    private String message;

    public GenerateTokenExcepetion() {
        this.message = "Token generation failed";
    }

    public GenerateTokenExcepetion(String message) {
        this.message = message;
    }

}
