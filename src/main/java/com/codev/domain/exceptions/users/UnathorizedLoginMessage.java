package com.codev.domain.exceptions.users;

import lombok.Data;

@Data
public class UnathorizedLoginMessage extends Exception {

    private String message;

    public UnathorizedLoginMessage() {
        this.message = "Email or password are incorrect";
    }

    public UnathorizedLoginMessage(String message) {
        this.message = message;
    }

}
