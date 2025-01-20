package com.drgym.drgym.repository;

import com.drgym.drgym.model.Friendship;
import com.drgym.drgym.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FriendshipRepositoryTest {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Friendship testFriendship;
    private User testUser1;
    private User testUser2;

    @BeforeEach
    void setUp() {
        testUser1 = new User();
        testUser1.setUsername("user1");
        testUser1.setEmail("user1@example.com");
        testUser1.setPassword(passwordEncoder.encode("password"));
        testUser1.setName("User");
        testUser1.setSurname("One");
        testUser1.setVerified(true);
        userRepository.save(testUser1);

        testUser2 = new User();
        testUser2.setUsername("user2");
        testUser2.setEmail("user2@example.com");
        testUser2.setPassword(passwordEncoder.encode("password"));
        testUser2.setName("User");
        testUser2.setSurname("Two");
        testUser2.setVerified(true);
        userRepository.save(testUser2);

        testFriendship = new Friendship();
        testFriendship.setFriend1Username("user1");
        testFriendship.setFriend2Username("user2");
        friendshipRepository.save(testFriendship);
    }

    @AfterEach
    void tearDown() {
        if (testFriendship != null) {
            friendshipRepository.delete(testFriendship);
        }
        if (testUser1 != null) {
            userRepository.deleteById(testUser1.getUsername());
        }
        if (testUser2 != null) {
            userRepository.deleteById(testUser2.getUsername());
        }
    }

    @Test
    void testFindByFriend1UsernameOrFriend2Username() {
        List<Friendship> friendships = friendshipRepository.findByFriend1UsernameOrFriend2Username("user1", "user2");
        assertFalse(friendships.isEmpty());
        assertEquals("user1", friendships.get(0).getFriend1Username());
        assertEquals("user2", friendships.get(0).getFriend2Username());
    }

    @Test
    void testExistsByFriend1UsernameAndFriend2Username() {
        boolean exists = friendshipRepository.existsByFriend1UsernameAndFriend2Username("user1", "user2");
        assertTrue(exists);
    }

    @Test
    void testFindFriendsUsernamesByUsername() {
        List<String> friends = friendshipRepository.findFriendsUsernamesByUsername("user1");
        assertFalse(friends.isEmpty());
        assertEquals("user2", friends.get(0));
    }
}