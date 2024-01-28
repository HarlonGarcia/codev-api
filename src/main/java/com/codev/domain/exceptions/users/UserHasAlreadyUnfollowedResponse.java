package com.codev.domain.exceptions.users;

import lombok.Data;

@Data
public class UserHasAlreadyUnfollowedResponse {

    private final String message;

    public UserHasAlreadyUnfollowedResponse(){
        this.message = "User has already unfollowed.";
    }

    public UserHasAlreadyUnfollowedResponse(String message){
        this.message = message;
    }

}
