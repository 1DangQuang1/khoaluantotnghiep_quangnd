package com.example.restapi.service;

import org.springframework.stereotype.Service;

import com.example.restapi.config.JwtUtil;
import com.example.restapi.dto.LoginRequest;
import com.example.restapi.dto.LoginResponse;
import com.example.restapi.model.User;
import com.example.restapi.repository.UserRepository;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));


        String token = jwtUtil.generate(user);
        return new LoginResponse(token, user.getId(), user.getName(), user.getRole());
    }


}