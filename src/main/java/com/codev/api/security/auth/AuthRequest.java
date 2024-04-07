package com.codev.api.security.auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class  AuthRequest {

    public String email;

    public String password;

}
