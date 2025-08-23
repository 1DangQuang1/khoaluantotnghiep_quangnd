package com.example.restapi.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String message;
    private Long id;
    private String name;
    private String role;

    public LoginResponse(String accessToken, Long id, String name, String role) {
        this.accessToken = accessToken;
        this.id = id;
        this.name = name;
        this.role = role;
        this.message = "Login successful";
    };
}
