package com.codev.domain.exceptions.challenges;

public class JoinNotAcceptedException extends Exception {
    public JoinNotAcceptedException() {
        super("Join not accepted: The user has already given a join.");
    }

    public JoinNotAcceptedException(String message) {
        super(message);
    }

    public JoinNotAcceptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public JoinNotAcceptedException(Throwable cause) {
        super("Join not accepted: The user has already given a join.", cause);
    }
}
