package com.drgym.drgym.controller;

import com.drgym.drgym.model.Friendship;
import com.drgym.drgym.model.FriendshipInvitation;
import com.drgym.drgym.service.FriendshipService;
import com.drgym.drgym.service.FriendshipService.UserFriendDTO;
import com.drgym.drgym.service.FriendshipService.FriendRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friends")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @GetMapping("friendsinfo/{username}")
    public Map<String, List<?>> getUserFriendsAndInvitations(@PathVariable String username) {
        List<UserFriendDTO> friends = friendshipService.getUserFriends(username);
        List<FriendRequestDTO> invitations = friendshipService.getUserFriendshipInvitations(username);
        Map<String, List<?>> response = new HashMap<>();
        response.put("friends", friends);
        response.put("invitations", invitations);
        return response;
    }

    @GetMapping("/isFriend/{user1}/{user2}")
    public boolean isFriend(@PathVariable String user1, @PathVariable String user2) {
        return friendshipService.areFriends(user1, user2);
    }

    @PostMapping("/sendRequest")
    public String sendFriendRequest(@RequestBody FriendRequestDTO friendRequest) {
        return friendshipService.sendFriendRequest(friendRequest.getSender(), friendRequest.getReceiver());
    }

    @PostMapping("/acceptRequest")
    public String acceptFriendRequest(@RequestParam Long invitationId) {
        return friendshipService.acceptFriendRequest(invitationId);
    }

    @PostMapping("/rejectRequest")
    public String rejectFriendRequest(@RequestParam Long invitationId) {
        return friendshipService.rejectFriendRequest(invitationId);
    }

    @DeleteMapping("/removeFriend")
    public String removeFriend(@RequestBody RemoveFriendRequest removeFriendRequest) {
        return friendshipService.removeFriend(removeFriendRequest.getUser1(), removeFriendRequest.getUser2());
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