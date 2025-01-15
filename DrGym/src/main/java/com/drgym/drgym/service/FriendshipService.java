package com.drgym.drgym.service;

import com.drgym.drgym.model.Friendship;
import com.drgym.drgym.model.FriendshipInvitation;
import com.drgym.drgym.repository.FriendshipRepository;
import com.drgym.drgym.repository.FriendshipInvitationRepository;
import com.drgym.drgym.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FriendshipService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private FriendshipInvitationRepository friendshipInvitationRepository;

    @Transactional
    public List<UserFriendDTO> getUserFriends(String username) {
        List<Friendship> friendships = friendshipRepository.findByFriend1UsernameOrFriend2Username(username, username);
        return friendships.stream()
                .map(friendship -> {
                    String friendUsername = friendship.getFriend1Username().equals(username) ? friendship.getFriend2Username() : friendship.getFriend1Username();
                    return new UserFriendDTO(friendUsername, friendship.getCreatedAt());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<FriendRequestDTO> getUserFriendshipInvitations(String username) {
        List<FriendshipInvitation> invitations = friendshipInvitationRepository.findByWhoReceiveUsername(username);
        return invitations.stream()
                .map(invitation -> new FriendRequestDTO(invitation.getId(), invitation.getWhoSendUsername(), invitation.getWhoReceiveUsername(), invitation.getSendTime()))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean areFriends(String user1, String user2) {
        return friendshipRepository.existsByFriend1UsernameAndFriend2Username(user1, user2) ||
                friendshipRepository.existsByFriend1UsernameAndFriend2Username(user2, user1);
    }

    @Transactional
    public String sendFriendRequest(String sender, String receiver) {
        if (friendshipRepository.existsByFriend1UsernameAndFriend2Username(sender, receiver) ||
                friendshipRepository.existsByFriend1UsernameAndFriend2Username(receiver, sender)) {
            return "You are already friends.";
        }
        if (friendshipInvitationRepository.existsInvitation(sender, receiver)) {
            return "Invitation already sent.";
        }
        if (friendshipInvitationRepository.existsInvitation(sender, receiver)) {
            return "Receiver has already sent an invitation. Please accept it.";
        }
        if (!userRepository.existsByUsername(receiver)) {
            return "There is no account associated with this username.";
        }
        FriendshipInvitation invitation = new FriendshipInvitation();
        invitation.setWhoSendUsername(sender);
        invitation.setWhoReceiveUsername(receiver);
        invitation.setSendTime(LocalDateTime.now());
        friendshipInvitationRepository.save(invitation);
        return "Request sent";
    }

    @Transactional
    public String acceptFriendRequest(Long invitationId) {
        FriendshipInvitation invitation = friendshipInvitationRepository.findById(invitationId)
                .orElseThrow(() -> new NoSuchElementException("Invitation not found with id: " + invitationId));
        Friendship friendship = new Friendship();
        friendship.setFriend1Username(invitation.getWhoSendUsername());
        friendship.setFriend2Username(invitation.getWhoReceiveUsername());
        friendship.setCreatedAt(LocalDateTime.now());
        friendshipRepository.save(friendship);
        return "Request accepted";
    }

    @Transactional
    public String rejectFriendRequest(Long invitationId) {
        try {
            friendshipInvitationRepository.deleteById(invitationId);
            return "Request rejected";
        } catch (Exception e) {
            throw new RuntimeException("Error rejecting friend request with invitationId: " + invitationId, e);
        }
    }

    @Transactional
    public String removeFriend(String user1, String user2) {
        friendshipRepository.deleteByFriend1UsernameAndFriend2Username(user1, user2);
        friendshipRepository.deleteByFriend1UsernameAndFriend2Username(user2, user1);
        return "Friend removed";
    }

    public String getInvitationReceiver(Long invitationId) {
        FriendshipInvitation invitation = friendshipInvitationRepository.findById(invitationId)
                .orElseThrow(() -> new NoSuchElementException("Invitation not found with id: " + invitationId));
        return invitation.getWhoReceiveUsername();
    }

    public static class UserFriendDTO {
        private String username;
        private LocalDateTime date;

        public UserFriendDTO(String username, LocalDateTime date) {
            this.username = username;
            this.date = date;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }
    }

    public static class FriendRequestDTO {
        private Long id;
        private String sender;
        private String receiver;

        public FriendRequestDTO() {}

        public FriendRequestDTO(Long id, String sender, String receiver, LocalDateTime sendTime) {
            this.id = id;
            this.sender = sender;
            this.receiver = receiver;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }
    }
}