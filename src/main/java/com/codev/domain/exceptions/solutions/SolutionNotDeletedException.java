package com.codev.domain.exceptions.solutions;

public class SolutionNotDeletedException extends Exception {
    public SolutionNotDeletedException(String message) {
        super("Solution not deleted: " + message);
    }

    public SolutionNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SolutionNotDeletedException(Throwable cause) {
        super("Solution not deleted: ", cause);
    }
}
