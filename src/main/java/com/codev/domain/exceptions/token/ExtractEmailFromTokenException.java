package com.codev.domain.exceptions.token;

public class ExtractEmailFromTokenException extends Exception {

    private final String message;

    public ExtractEmailFromTokenException() {
        super("Incorrect token or the token's 'subject' does not represent an email address.");
        this.message = "Incorrect token or the token's 'subject' does not represent an email address.";
    }

    public ExtractEmailFromTokenException(String message){
        super(message);
        this.message = message;
    }

}
