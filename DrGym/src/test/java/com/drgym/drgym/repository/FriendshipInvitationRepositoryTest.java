package com.drgym.drgym.repository;

import com.drgym.drgym.model.FriendshipInvitation;
import com.drgym.drgym.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FriendshipInvitationRepositoryTest {

    @Autowired
    private FriendshipInvitationRepository friendshipInvitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private FriendshipInvitation testInvitation;
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

        testInvitation = new FriendshipInvitation();
        testInvitation.setWhoSendUsername("user1");
        testInvitation.setWhoReceiveUsername("user2");
        testInvitation.setSendTime(LocalDateTime.now());
        friendshipInvitationRepository.save(testInvitation);
    }

    @AfterEach
    void tearDown() {
        if (testInvitation != null) {
            friendshipInvitationRepository.delete(testInvitation);
        }
        if (testUser1 != null) {
            userRepository.deleteById(testUser1.getUsername());
        }
        if (testUser2 != null) {
            userRepository.deleteById(testUser2.getUsername());
        }
    }

    @Test
    void testFindByWhoReceiveUsername() {
        List<FriendshipInvitation> invitations = friendshipInvitationRepository.findByWhoReceiveUsername("user2");
        assertFalse(invitations.isEmpty());
        assertEquals("user1", invitations.get(0).getWhoSendUsername());
    }

    @Test
    void testExistsInvitation() {
        boolean exists = friendshipInvitationRepository.existsInvitation("user1", "user2");
        assertTrue(exists);
    }
}