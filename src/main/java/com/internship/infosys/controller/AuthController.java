package com.internship.infosys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internship.infosys.dto.LoginRequest;
import com.internship.infosys.dto.LoginResponse;
import com.internship.infosys.dto.RegisterRequest;
import com.internship.infosys.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://frontend-1xoh.onrender.com")
public class AuthController {

    @Autowired
    private AuthService authService;

    // =====================================================
    // REGISTER
    // =====================================================

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        String message = authService.register(request);

        return ResponseEntity.ok(message);
    }

    // =====================================================
    // LOGIN
    // =====================================================

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    // =====================================================
    // VERIFY EMAIL
    // =====================================================

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(
            @RequestParam("token") String token) {

        String message = authService.verifyEmail(token);

        return ResponseEntity.ok(message);
    }
}