package com.codev.domain.exceptions.challenges;

public class CategoryAlreadyExistsInChallenge extends Throwable {

    public CategoryAlreadyExistsInChallenge() {
        super("Category already exists in challenge, so it cannot overwrite");
    }

    public CategoryAlreadyExistsInChallenge(String message) {
        super(message);
    }

    public CategoryAlreadyExistsInChallenge(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryAlreadyExistsInChallenge(Throwable cause) {
        super("Category already exists in challenge, so it cannot overwrite", cause);
    }

}
