package com.codev.domain.dto.view;

import com.codev.api.security.auth.AuthResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDTOView {
    public String token;

    public AuthDTOView(AuthResponse authResponse){
        this.token = authResponse.getToken();
    }
}
