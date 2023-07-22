package com.codev.domain.exceptions.users;

public class UserDeactivatedException extends Exception {
    public UserDeactivatedException() {
        super("User is deactivated.");
    }

    public UserDeactivatedException(String message) {
        super(message);
    }

    public UserDeactivatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDeactivatedException(Throwable cause) {
        super("User is deactivated.", cause);
    }
}
