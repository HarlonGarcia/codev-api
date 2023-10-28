package com.codev.domain.exceptions.users;

import lombok.Data;

@Data
public class InvalidLoginResponse {

    private String message;

    public InvalidLoginResponse(){
        this.message = "Username or password incorrect!";
    }

    public InvalidLoginResponse(String message){
        this.message = message;
    }

}
