package com.drgym.drgym.service;

import com.drgym.drgym.model.User;
import com.drgym.drgym.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setName("Test");
        user.setSurname("User");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setVerified(true);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        if (user != null && user.getUsername() != null) {
            userRepository.deleteById(user.getUsername());
        }
    }

    @Test
    void testFindByUsername() {
        Optional<User> foundUser = userService.findByUsername("testuser");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void testFindByEmail() {
        Optional<User> foundUser = userService.findByEmail("testuser@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser@example.com", foundUser.get().getEmail());
    }

    @Test
    void testSaveUser() {
        User newUser = new User();
        newUser.setUsername("testuser2");
        newUser.setName("Test");
        newUser.setSurname("User");
        newUser.setEmail("testuser2@example.com");
        newUser.setPassword("password");
        newUser.setVerified(true);
        userService.saveUser(newUser);

        Optional<User> savedUser = userRepository.findById("testuser2");
        assertTrue(savedUser.isPresent());
        assertEquals("testuser2", savedUser.get().getUsername());
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser("testuser");
        Optional<User> deletedUser = userRepository.findById("testuser");
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setName("Updated");
        updatedUser.setSurname("User");
        updatedUser.setWeight(70.0);
        updatedUser.setHeight(180.0);
        updatedUser.setFavoriteExercise(1L);
        updatedUser.setAvatar("updated_avatar.png");

        Optional<User> result = userService.updateUser("testuser", updatedUser);
        assertTrue(result.isPresent());
        assertEquals("Updated", result.get().getName());
        assertEquals("User", result.get().getSurname());
        assertEquals(70.0, result.get().getWeight());
        assertEquals(180.0, result.get().getHeight());
        assertEquals(1L, result.get().getFavoriteExercise());
        assertEquals("updated_avatar.png", result.get().getAvatar());
    }
}