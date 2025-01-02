package com.drgym.drgym.controller;

import com.drgym.drgym.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/auth/verification")
    public String verifyEmail(@RequestParam String email, @RequestParam String token) {
        boolean isVerified = authService.verifyEmail(email, token);
        return isVerified ? "Email verified for " + email : "Verification failed";
    }

    @GetMapping("/auth/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String token, @RequestParam String newPassword) {
        boolean isReset = authService.resetPassword(email, token, newPassword);
        return isReset ? "Password reset for " + email : "Reset password failed";
    }

    @GetMapping("/auth/generate-token")
    public String generateToken(@RequestParam String email) {
        authService.generateToken(email);
        return "Token generated and email sent";
    }

    @GetMapping("/auth/send-password-reset-token")
    public String sendPasswordResetToken(@RequestParam String email) {
        authService.sendPasswordResetToken(email);
        return "Password reset token sent to " + email;
    }
}