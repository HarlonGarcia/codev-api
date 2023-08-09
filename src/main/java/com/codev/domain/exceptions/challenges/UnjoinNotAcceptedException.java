package com.codev.domain.exceptions.challenges;

public class UnjoinNotAcceptedException extends Exception {
    public UnjoinNotAcceptedException() {
        super("Unjoin not accepted: The user has already unjoined the challenge.");
    }

    public UnjoinNotAcceptedException(String message) {
        super(message);
    }

    public UnjoinNotAcceptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnjoinNotAcceptedException(Throwable cause) {
        super("Join not accepted: The user has already unjoined the challenge.", cause);
    }
}
