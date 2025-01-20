package com.drgym.drgym.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    private Token token;

    @BeforeEach
    void setUp() {
        token = new Token("test@test.com", "verificationToken", "resetToken", new Date());
    }

    @Test
    void testTokenConstructor() {
        assertEquals("test@test.com", token.getEmail());
        assertEquals("verificationToken", token.getVerificationToken());
        assertEquals("resetToken", token.getResetToken());
        assertNotNull(token.getResetTokenExpiry());
    }

    @Test
    void testSetEmail() {
        token.setEmail("updated@test.com");
        assertEquals("updated@test.com", token.getEmail());
    }

    @Test
    void testSetVerificationToken() {
        token.setVerificationToken("updatedVerificationToken");
        assertEquals("updatedVerificationToken", token.getVerificationToken());
    }

    @Test
    void testSetResetToken() {
        token.setResetToken("updatedResetToken");
        assertEquals("updatedResetToken", token.getResetToken());
    }

    @Test
    void testSetResetTokenExpiry() {
        Date newExpiry = new Date();
        token.setResetTokenExpiry(newExpiry);
        assertEquals(newExpiry, token.getResetTokenExpiry());
    }
}