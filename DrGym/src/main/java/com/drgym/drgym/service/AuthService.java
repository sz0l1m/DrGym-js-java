package com.drgym.drgym.service;

import com.drgym.drgym.model.Token;
import com.drgym.drgym.model.User;
import com.drgym.drgym.model.UserRegistrationRequest;
import com.drgym.drgym.repository.TokenRepository;
import com.drgym.drgym.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public ResponseEntity<?> login(String identifier, String password, HttpServletResponse response) {
        Optional<User> userOptional = userRepository.findByEmail(identifier);
        if (!userOptional.isPresent()) {
            userOptional = userRepository.findByUsername(identifier);
        }
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            User user = userOptional.get();
            String token = Jwts.builder()
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .signWith(SECRET_KEY)
                    .compact();

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(86400);
            cookie.setAttribute("SameSite", "Lax");

            response.addCookie(cookie);

            return ResponseEntity.ok(new LoginResponse(user.getUsername()));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }

    public static class LoginResponse {
        private String username;

        public LoginResponse(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(400).body("E-mail is already taken");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(400).body("Username is already taken");
        }
        User user = new User(request.getUsername(), request.getName(), request.getSurname(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getWeight(), request.getHeight());
        userRepository.save(user);
        String verificationToken = UUID.randomUUID().toString();
        Token token = new Token(request.getEmail(), verificationToken);
        tokenRepository.save(token);
        String link = "http://localhost:8080/auth/verification?email=" + request.getEmail() + "&token=" + verificationToken;
        emailService.sendVerificationEmail(request.getEmail(), link);
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> verify(String email, String token) {
        Token verificationToken = tokenRepository.findByEmailAndVerificationToken(email, token);
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
        verificationToken.setVerificationToken(null);
        tokenRepository.save(verificationToken);
        return ResponseEntity.ok("User verified successfully");
    }

    public ResponseEntity<?> forgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(400).body("User does not exist");
        }
        String resetToken = UUID.randomUUID().toString();
        Token token = tokenRepository.findById(email).orElse(new Token(email, null, resetToken, new Date(System.currentTimeMillis() + 3600000))); // 1 hour expiry
        token.setResetToken(resetToken);
        token.setResetTokenExpiry(new Date(System.currentTimeMillis() + 300000));
        tokenRepository.save(token);
        String link = "http://localhost:8080/auth/reset-password?email=" + email + "&token=" + resetToken;
        emailService.sendPasswordResetEmail(email, link);
        return ResponseEntity.ok("Reset password email sent");
    }

    public ResponseEntity<?> resetPassword(String email, String newPassword, String token) {
        Token resetToken = tokenRepository.findByEmailAndResetToken(email, token);
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
        resetToken.setResetToken(null);
        resetToken.setResetTokenExpiry(null);
        tokenRepository.save(resetToken);
        return ResponseEntity.ok("Password reset successfully");
    }
}