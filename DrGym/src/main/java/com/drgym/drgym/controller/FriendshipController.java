package com.drgym.drgym.controller;

import jakarta.servlet.http.HttpServletRequest;
import com.drgym.drgym.service.FriendshipService;
import com.drgym.drgym.service.FriendshipService.UserFriendDTO;
import com.drgym.drgym.service.FriendshipService.FriendRequestDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friends")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserController userController;

    @GetMapping("/friendsinfo/{username}")
    public ResponseEntity<?> getUserFriendsAndInvitations(@PathVariable String username, HttpServletRequest request) {
        if (!userController.tokenOwner(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        List<UserFriendDTO> friends = friendshipService.getUserFriends(username);
        List<FriendRequestDTO> invitations = friendshipService.getUserFriendshipInvitations(username);
        Map<String, List<?>> response = new HashMap<>();
        response.put("friends", friends);
        response.put("invitations", invitations);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/isFriend/{user1}/{user2}")
    public ResponseEntity<?> isFriend(@PathVariable String user1, @PathVariable String user2, HttpServletRequest request) {
        if (!userController.tokenOwnerOrFriend(user1, request) && !userController.tokenOwnerOrFriend(user2, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }
        boolean areFriends = friendshipService.areFriends(user1, user2);
        return ResponseEntity.ok(areFriends);
    }

    @PostMapping("/sendRequest")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequestDTO friendRequest, HttpServletRequest request) {
        if (!userController.tokenOwner(friendRequest.getSender(), request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }
        String result = friendshipService.sendFriendRequest(friendRequest.getSender(), friendRequest.getReceiver());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/acceptRequest")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam Long invitationId, HttpServletRequest request) {
        String username = friendshipService.getInvitationReceiver(invitationId);
        if (!userController.tokenOwner(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }
        String result = friendshipService.acceptFriendRequest(invitationId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/rejectRequest")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam Long invitationId, HttpServletRequest request) {
        String username = friendshipService.getInvitationReceiver(invitationId);
        if (!userController.tokenOwner(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }
        String result = friendshipService.rejectFriendRequest(invitationId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/removeFriend")
    public ResponseEntity<String> removeFriend(@RequestBody RemoveFriendRequest removeFriendRequest, HttpServletRequest request) {
        String user1 = removeFriendRequest.getUser1();
        String user2 = removeFriendRequest.getUser2();
        if (!userController.tokenOwnerOrFriend(user1, request) && !userController.tokenOwnerOrFriend(user2, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }
        String result = friendshipService.removeFriend(removeFriendRequest.getUser1(), removeFriendRequest.getUser2());
        return ResponseEntity.ok(result);
    }

    public static class RemoveFriendRequest {
        private String user1;
        private String user2;

        public String getUser1() {
            return user1;
        }

        public String getUser2() {
            return user2;
        }
    }
}