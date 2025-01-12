package com.drgym.drgym.repository;

import com.drgym.drgym.model.FriendshipInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipInvitationRepository extends JpaRepository<FriendshipInvitation, Long> {
    List<FriendshipInvitation> findByWhoReceiveUsername(String username);
}