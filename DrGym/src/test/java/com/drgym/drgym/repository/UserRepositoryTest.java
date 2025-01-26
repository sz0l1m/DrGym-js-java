package com.drgym.drgym.repository;

import com.drgym.drgym.model.User;
import com.drgym.drgym.model.Friendship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private User friendUser;
    private Friendship testFriendship;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setName("Test");
        testUser.setSurname("User");
        testUser.setVerified(true);
        userRepository.save(testUser);

        friendUser = new User();
        friendUser.setUsername("frienduser");
        friendUser.setEmail("frienduser@example.com");
        friendUser.setPassword(passwordEncoder.encode("password"));
        friendUser.setName("Friend");
        friendUser.setSurname("User");
        friendUser.setVerified(true);
        userRepository.save(friendUser);

        testFriendship = new Friendship();
        testFriendship.setFriend1Username("testuser");
        testFriendship.setFriend2Username("frienduser");
        friendshipRepository.save(testFriendship);
    }

    @AfterEach
    void tearDown() {
        if (testUser != null) {
            userRepository.deleteById(testUser.getUsername());
        }
        if (friendUser != null) {
            userRepository.deleteById(friendUser.getUsername());
        }
        if (testFriendship != null) {
            friendshipRepository.delete(testFriendship);
        }
    }

    @Test
    void testFindByEmail() {
        Optional<User> user = userRepository.findByEmail("testuser@example.com");
        assertTrue(user.isPresent());
        assertEquals("testuser", user.get().getUsername());
    }

    @Test
    void testFindByUsername() {
        Optional<User> user = userRepository.findByUsername("testuser");
        assertTrue(user.isPresent());
        assertEquals("testuser@example.com", user.get().getEmail());
    }

    @Test
    void testExistsByEmail() {
        assertTrue(userRepository.existsByEmail("testuser@example.com"));
    }

    @Test
    void testExistsByUsername() {
        assertTrue(userRepository.existsByUsername("testuser"));
    }

    @Test
    void testFindByUsernameContaining() {
        List<User> users = userRepository.findByUsernameContaining("test");
        assertFalse(users.isEmpty());
        assertEquals("testuser", users.get(0).getUsername());
    }

    @Test
    void testAreUsersFriends() {
        assertTrue(userRepository.areUsersFriends("testuser", "frienduser"));
    }
}