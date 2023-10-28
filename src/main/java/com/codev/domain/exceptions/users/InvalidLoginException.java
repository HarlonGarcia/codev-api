package com.codev.domain.exceptions.users;

public class InvalidLoginException extends Exception {

    public InvalidLoginException() {
        super("Username or password incorrect!");
    }

    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLoginException(Throwable cause) {
        super("Username or password incorrect!", cause);
    }

}
