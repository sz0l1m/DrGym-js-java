package com.drgym.drgym.service;

import com.drgym.drgym.model.Friendship;
import com.drgym.drgym.model.FriendshipInvitation;
import com.drgym.drgym.model.User;
import com.drgym.drgym.repository.FriendshipRepository;
import com.drgym.drgym.repository.FriendshipInvitationRepository;
import com.drgym.drgym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendshipServiceTest {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private FriendshipInvitationRepository friendshipInvitationRepository;

    private User user1;
    private User user2;
    private FriendshipInvitation invitation;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setUsername("user1");
        user1.setName("User");
        user1.setSurname("One");
        user1.setEmail("user1@example.com");
        user1.setPassword("password");
        user1.setVerified(true);
        userRepository.save(user1);

        user2 = new User();
        user2.setUsername("user2");
        user2.setName("User");
        user2.setSurname("Two");
        user2.setEmail("user2@example.com");
        user2.setPassword("password");
        user2.setVerified(true);
        userRepository.save(user2);

        invitation = new FriendshipInvitation();
        invitation.setWhoSendUsername("user1");
        invitation.setWhoReceiveUsername("user2");
        invitation.setSendTime(LocalDateTime.now());
        friendshipInvitationRepository.save(invitation);
    }

    @Test
    void testAreFriends() {
        friendshipService.acceptFriendRequest(invitation.getId());
        assertTrue(friendshipService.areFriends("user1", "user2"));
    }

    @Test
    void testSendFriendRequest() {
        String response = friendshipService.sendFriendRequest("user1", "user2");
        assertEquals("Invitation already sent.", response);
    }

    @Test
    void testAcceptFriendRequest() {
        String response = friendshipService.acceptFriendRequest(invitation.getId());
        assertEquals("Request accepted", response);
        assertTrue(friendshipRepository.existsByFriend1UsernameAndFriend2Username("user1", "user2"));
    }

    @Test
    void testRejectFriendRequest() {
        String response = friendshipService.rejectFriendRequest(invitation.getId());
        assertEquals("Request rejected", response);
        assertFalse(friendshipInvitationRepository.existsById(invitation.getId()));
    }

    @Test
    void testRemoveFriend() {
        friendshipService.acceptFriendRequest(invitation.getId());
        String response = friendshipService.removeFriend("user1", "user2");
        assertEquals("Friend removed", response);
        assertFalse(friendshipService.areFriends("user1", "user2"));
    }

    @Test
    void testGetInvitationReceiver() {
        String receiver = friendshipService.getInvitationReceiver(invitation.getId());
        assertEquals("user2", receiver);
    }

    @Test
    void testGetFriendsUsernames() {
        friendshipService.acceptFriendRequest(invitation.getId());
        List<String> friendsUsernames = friendshipService.getFriendsUsernames("user1");
        assertFalse(friendsUsernames.isEmpty());
        assertEquals("user2", friendsUsernames.get(0));
    }
}