package com.drgym.drgym.controller;

import com.drgym.drgym.model.UserLoginRequest;
import com.drgym.drgym.model.UserForgotPasswordRequest;
import com.drgym.drgym.model.UserResetPasswordRequest;
import com.drgym.drgym.model.UserRegistrationRequest;
import com.drgym.drgym.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        return authService.login(request.getEmail(), request.getPassword());
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