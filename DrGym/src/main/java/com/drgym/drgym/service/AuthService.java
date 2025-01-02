package com.drgym.drgym.service;

import com.drgym.drgym.model.Token;
import com.drgym.drgym.model.User;
import com.drgym.drgym.repository.TokenRepository;
import com.drgym.drgym.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public ResponseEntity<?> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            String token = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .signWith(SECRET_KEY)
                    .compact();
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    public ResponseEntity<?> register(String username, String name, String surname, String email, String password, Double weight, Double height) {
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(400).body("User already exists");
        }
        User user = new User(username, name, surname, email, passwordEncoder.encode(password), weight, height);
        userRepository.save(user);
        String token = UUID.randomUUID().toString();
        tokenRepository.save(new Token(email, token));
        String link = "http://localhost:3000/auth/verification?email=" + email + "&token=" + token;
        emailService.sendVerificationEmail(email, link);
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> verify(String email, String token) {
        Token verificationToken = tokenRepository.findByEmailAndToken(email, token);
        if (verificationToken == null) {
            return ResponseEntity.status(400).body("Invalid token");
        }
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(400).body("User does not exist");
        }
        User user = userOptional.get();
        if (user.isVerified()) {
            return ResponseEntity.status(400).body("User is already verified");
        }
        user.setVerified(true);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);
        return ResponseEntity.ok("User verified successfully");
    }

    public ResponseEntity<?> forgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(400).body("User does not exist");
        }
        String token = UUID.randomUUID().toString();
        tokenRepository.save(new Token(email, token));
        String link = "http://localhost:3000/auth/reset-password?email=" + email + "&token=" + token;
        emailService.sendPasswordResetEmail(email, link);
        return ResponseEntity.ok("Reset password email sent");
    }

    public ResponseEntity<?> resetPassword(String email, String newPassword, String token) {
        Token resetToken = tokenRepository.findByEmailAndToken(email, token);
        if (resetToken == null) {
            return ResponseEntity.status(400).body("Invalid token");
        }
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(400).body("User does not exist");
        }
        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
        return ResponseEntity.ok("Password reset successfully");
    }
}