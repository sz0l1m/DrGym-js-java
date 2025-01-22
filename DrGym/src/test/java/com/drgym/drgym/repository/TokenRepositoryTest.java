package com.drgym.drgym.repository;

import com.drgym.drgym.model.Token;
import com.drgym.drgym.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Token testToken;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@test.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setName("Test");
        testUser.setSurname("User");
        testUser.setVerified(true);
        userRepository.save(testUser);

        testToken = new Token("test@test.com", "verificationToken", "resetToken", new Date(System.currentTimeMillis() + 10000)); // Ensure resetTokenExpiry is in the future
        tokenRepository.save(testToken);
    }

    @AfterEach
    void tearDown() {
        if (testToken != null) {
            tokenRepository.delete(testToken);
        }
        if (testUser != null) {
            userRepository.deleteById(testUser.getUsername());
        }
    }

    @Test
    void testFindByEmailAndVerificationToken() {
        Token token = tokenRepository.findByEmailAndVerificationToken("test@test.com", "verificationToken");
        assertNotNull(token);
        assertEquals("test@test.com", token.getEmail());
    }

    @Test
    void testFindByEmailAndResetToken() {
        Token token = tokenRepository.findByEmailAndResetToken("test@test.com", "resetToken");
        assertNotNull(token);
        assertEquals("test@test.com", token.getEmail());
    }

    @Test
    @Transactional
    void testDeleteAllByEmail() {
        tokenRepository.deleteAllByEmail("test@test.com");
        Token token = tokenRepository.findByEmailAndVerificationToken("test@test.com", "verificationToken");
        assertNull(token);
    }
}