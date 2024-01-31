package com.codev.domain.exceptions.users;

import lombok.Data;

@Data
public class UserIsAlreadyBeingFollowedResponse {

    private final String message;

    public UserIsAlreadyBeingFollowedResponse(){
        this.message = "User is already being followed.";
    }

    public UserIsAlreadyBeingFollowedResponse(String message){
        this.message = message;
    }

}
