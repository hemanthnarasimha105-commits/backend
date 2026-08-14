package com.internship.infosys.service;

import com.internship.infosys.dto.LoginRequest;
import com.internship.infosys.dto.LoginResponse;
import com.internship.infosys.dto.RegisterRequest;

public interface AuthService {

    // Register
    String register(RegisterRequest request);

    // Login
    LoginResponse login(LoginRequest request);

    // Email verification
    String verifyEmail(String token);
}