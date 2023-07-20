package com.codev.domain.exceptions.solutions;

public class LikeNotAcceptedException extends Exception {
    public LikeNotAcceptedException() {
        super("Like not accepted: The user has already given a like.");
    }

    public LikeNotAcceptedException(String message) {
        super(message);
    }

    public LikeNotAcceptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LikeNotAcceptedException(Throwable cause) {
        super("Like not accepted: The user has already given a like.", cause);
    }
}
