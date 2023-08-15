package com.codev.domain.exceptions.users;

import lombok.Data;

@Data
public class UserDoesNotExistResponse {

    private final String message;

    public UserDoesNotExistResponse(){
        this.message = "User does not exist";
    }

    public UserDoesNotExistResponse(String message){
        this.message = message;
    }

}
