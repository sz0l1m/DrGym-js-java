package com.drgym.drgym.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("ziutson", "test", "test", "test@test.test", "test", 80.0, 180.0);
    }

    @Test
    void testUserConstructor() {
        assertEquals("ziutson", user.getUsername());
        assertEquals("test", user.getName());
        assertEquals("test", user.getSurname());
        assertEquals("test@test.test", user.getEmail());
        assertEquals("test", user.getPassword());
        assertEquals(80.0, user.getWeight());
        assertEquals(180.0, user.getHeight());
        assertFalse(user.isVerified());
    }

    @Test
    void testSetUsername() {
        user.setUsername("updatedUsername");
        assertEquals("updatedUsername", user.getUsername());
    }

    @Test
    void testSetName() {
        user.setName("Updated Name");
        assertEquals("Updated Name", user.getName());
    }

    @Test
    void testSetSurname() {
        user.setSurname("Updated Surname");
        assertEquals("Updated Surname", user.getSurname());
    }

    @Test
    void testSetEmail() {
        user.setEmail("updatedemail@test.com");
        assertEquals("updatedemail@test.com", user.getEmail());
    }

    @Test
    void testSetPassword() {
        user.setPassword("newPassword123");
        assertEquals("newPassword123", user.getPassword());
    }

    @Test
    void testSetWeight() {
        user.setWeight(95.0);
        assertEquals(95.0, user.getWeight());
    }

    @Test
    void testSetHeight() {
        user.setHeight(195.0);
        assertEquals(195.0, user.getHeight());
    }

    @Test
    void testSetVerified() {
        user.setVerified(true);
        assertTrue(user.isVerified());
    }

    @Test
    void testSetFavoriteExercise() {
        user.setFavoriteExercise(123L);
        assertEquals(123L, user.getFavoriteExercise());
    }

    @Test
    void testSetAvatar() {
        user.setAvatar("avatar.png");
        assertEquals("avatar.png", user.getAvatar());
    }

    @Test
    void testSetToken() {
        user.setToken("token123");
        assertEquals("token123", user.getToken());
    }

    @Test
    void testSetIdentifier() {
        user.setIdentifier("identifier123");
        assertEquals("identifier123", user.getIdentifier());
    }

    @Test
    void testSetNewPassword() {
        user.setNewPassword("newPassword");
        assertEquals("newPassword", user.getNewPassword());
    }
}