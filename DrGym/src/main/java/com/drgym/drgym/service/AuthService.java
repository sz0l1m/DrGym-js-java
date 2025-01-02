package com.drgym.drgym.service;

import com.drgym.drgym.model.Token;
import com.drgym.drgym.repository.TokenRepository;
import com.drgym.drgym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    public boolean verifyEmail(String email, String token) {
        if (tokenRepository.isValidToken(email, token)) {
            userRepository.verifyEmail(email);
            return true;
        }
        return false;
    }

    public boolean resetPassword(String email, String token, String newPassword) {
        if (tokenRepository.isValidToken(email, token)) {
            userRepository.resetPassword(email, newPassword);
            return true;
        }
        return false;
    }

    public String generateToken(String email) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setEmail(email);
        token.setToken(tokenValue);
        tokenRepository.save(token);

        String link = "http://localhost:8080/auth/verification?email=" + email + "&token=" + tokenValue;
        emailService.sendVerificationEmail(email, link);

        return tokenValue;
    }

    public void sendPasswordResetToken(String email) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setEmail(email);
        token.setToken(tokenValue);
        tokenRepository.save(token);

        String link = "http://localhost:8080/auth/reset-password?email=" + email + "&token=" + tokenValue;
        emailService.sendPasswordResetEmail(email, link);
    }
}