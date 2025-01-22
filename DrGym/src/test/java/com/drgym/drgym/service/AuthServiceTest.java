package com.drgym.drgym.service;

import static org.junit.jupiter.api.Assertions.*;

import com.drgym.drgym.model.Token;
import com.drgym.drgym.model.User;
import com.drgym.drgym.repository.TokenRepository;
import com.drgym.drgym.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @AfterEach
    void tearDown() {
        if (testUser != null) {
            userRepository.deleteById(testUser.getUsername());
            tokenRepository.deleteAllByEmail(testUser.getEmail());
        }
    }

    @Test
    void testLogin() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setUsername("testuser");
        testUser.setPassword(passwordEncoder.encode("testpassword")); // Encode the password
        testUser.setName("Test");
        testUser.setSurname("User");
        testUser.setVerified(true);
        userRepository.save(testUser);

        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        ResponseEntity<?> responseEntity = authService.login("test@example.com", "testpassword", mockResponse);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(mockResponse.getHeader("Set-Cookie"), "Set-Cookie header should not be null");
    }

    @Test
    void testRegister() {
        testUser = new User();
        testUser.setEmail("new@example.com");
        testUser.setUsername("newuser");
        testUser.setPassword("newpassword");
        testUser.setName("New");
        testUser.setSurname("User");

        ResponseEntity<?> responseEntity = authService.register(testUser);

        assertEquals(200, responseEntity.getStatusCodeValue());
        Optional<User> registeredUser = userRepository.findByEmail("new@example.com");
        assertTrue(registeredUser.isPresent());
        assertEquals("newuser", registeredUser.get().getUsername());
    }

    @Test
    void testVerify() {
        testUser = new User();
        testUser.setEmail("verify@example.com");
        testUser.setUsername("verifyuser");
        testUser.setPassword("verifypassword");
        testUser.setName("Verify");
        testUser.setSurname("User");
        userRepository.save(testUser);

        String verificationToken = UUID.randomUUID().toString();
        Token token = new Token(testUser.getEmail(), verificationToken);
        tokenRepository.save(token);

        ResponseEntity<?> responseEntity = authService.verify(testUser.getEmail(), verificationToken);

        assertEquals(200, responseEntity.getStatusCodeValue());
        Optional<User> verifiedUser = userRepository.findByEmail("verify@example.com");
        assertTrue(verifiedUser.isPresent());
        assertTrue(verifiedUser.get().isVerified());
    }
}