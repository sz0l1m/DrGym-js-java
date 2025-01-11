package com.drgym.drgym.service;

import com.drgym.drgym.model.User;
import com.drgym.drgym.repository.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletResponse response;

    @Mock
    private EmailService emailService;

    @Mock
    private TokenRepository tokenRepository;

    private Key secretKey;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        secretKey = Keys.hmacShaKeyFor("jebaczydowjebaczydowJWT12345678901234567890123456789012".getBytes());
        authService = new AuthService(userRepository, tokenRepository, passwordEncoder, emailService, secretKey);
    }

    @Test
    public void testLoginSuccess() {
        User user = new User("skuter", "Kacper", "Siemionek", "siemionek.kacper22@gmail.com", "Gimpson123", 70.0, 175.0);
        user.setVerified(true);

        when(userRepository.findByEmail("siemionek.kacper22@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Gimpson123", user.getPassword())).thenReturn(true);

        ResponseEntity<?> responseEntity = authService.login("siemionek.kacper22@gmail.com", "Gimpson123", response);

        System.out.printf("responseEntity: %s\n", responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(response).addCookie(any(Cookie.class));
    }

    @Test
    public void testLoginInvalidCredentials() {
        when(userRepository.findByEmail("email@example.com")).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = authService.login("email@example.com", "wrongpassword", response);

        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testLoginUserNotVerified() {
        User user = new User("skuter", "Kacper", "Siemionek", "siemionek.kacper22@gmail.com", "Gimpson123", 70.0, 175.0);
        user.setVerified(false);

        when(userRepository.findByEmail("siemionek.kacper22@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Gimpson123", user.getPassword())).thenReturn(true);

        ResponseEntity<?> responseEntity = authService.login("siemionek.kacper22@gmail.com", "Gimpson123", response);

        assertEquals(403, responseEntity.getStatusCodeValue());
    }
}