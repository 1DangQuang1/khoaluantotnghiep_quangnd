package com.example.restapi.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public AuthResponse login(AuthRequest request) {
        // Dummy authentication logic for demo
        if ("user".equals(request.getUsername()) && "secret".equals(request.getPassword())) {
            User user = new User("1", "user", "doctor");
            return new AuthResponse("jwt...", user);
        }
        return null;
    }
}
