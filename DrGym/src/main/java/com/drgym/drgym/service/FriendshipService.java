package com.drgym.drgym.service;

import com.drgym.drgym.model.Friendship;
import com.drgym.drgym.model.FriendshipInvitation;
import com.drgym.drgym.model.User;
import com.drgym.drgym.repository.FriendshipRepository;
import com.drgym.drgym.repository.FriendshipInvitationRepository;
import com.drgym.drgym.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    private DataSource dataSource;

    public List<UserFriendDTO> getUserFriends(String username) {
        List<UserFriendDTO> friends = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call GET_USER_FRIENDS_WITH_AVATAR(?, ?)}")) {
            callableStatement.setString(1, username);
            callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
            callableStatement.execute();
            try (ResultSet rs = (ResultSet) callableStatement.getObject(2)) {
                while (rs.next()) {
                    String friendUsername = rs.getString("FRIEND_USERNAME");
                    String avatar = rs.getString("AVATAR");
                    friends.add(new UserFriendDTO(friendUsername, null, avatar));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching friends with avatar", e);
        }
        return friends;
    }

    public List<FriendRequestDTO> getUserFriendshipInvitations(String username) {
        List<FriendRequestDTO> requests = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call GET_USER_FRIEND_REQUESTS_WITH_AVATAR(?, ?)}")) {
            callableStatement.setString(1, username);
            callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
            callableStatement.execute();
            try (ResultSet rs = (ResultSet) callableStatement.getObject(2)) {
                while (rs.next()) {
                    Long id = rs.getLong("ID");
                    String sender = rs.getString("WHO_SEND_USERNAME");
                    String receiver = rs.getString("WHO_RECEIVE_USERNAME");
                    String avatar = rs.getString("AVATAR");
                    requests.add(new FriendRequestDTO(id, sender, receiver, avatar));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching friend requests with avatar", e);
        }
        return requests;
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

    public List<String> getFriendsUsernames(String username) {
        return friendshipRepository.findFriendsUsernamesByUsername(username);
    }

    public static class UserFriendDTO {
        private String username;
        private LocalDateTime date;
        private String avatar;

        public UserFriendDTO(String username, LocalDateTime date, String avatar) {
            this.username = username;
            this.avatar = avatar;
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

        public String getAvatar() { return avatar; }

        public void setAvatar(String avatar) { this.avatar = avatar; }
    }

    public static class FriendRequestDTO {
        private Long id;
        private String sender;
        private String receiver;
        private String avatar;

        public FriendRequestDTO() {}

        public FriendRequestDTO(Long id, String sender, String receiver, String avatar) {
            this.id = id;
            this.sender = sender;
            this.receiver = receiver;
            this.avatar = avatar;
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

        public String getAvatar() { return avatar; }

        public void setAvatar(String avatar) { this.avatar = avatar; }
    }
}