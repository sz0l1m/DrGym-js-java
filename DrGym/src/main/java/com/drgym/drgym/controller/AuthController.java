package com.drgym.drgym.controller;

import com.drgym.drgym.model.*;
import com.drgym.drgym.repository.UserRepository;
import com.drgym.drgym.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request, HttpServletResponse response) {
        return authService.login(request.getIdentifier(), request.getPassword(), response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        return authService.logout(response);
    }

    @PostMapping("/verification")
    public ResponseEntity<?> verify(@RequestParam String email, @RequestParam String token) {
        return authService.verify(email, token);
    }

        @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody UserForgotPasswordRequest request) {
        return authService.forgotPassword(request.getEmail());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody UserResetPasswordRequest request) {
        return authService.resetPassword(request.getEmail(), request.getNewPassword(), request.getToken());
    }
}