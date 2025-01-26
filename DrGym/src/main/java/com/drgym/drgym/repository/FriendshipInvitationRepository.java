package com.drgym.drgym.repository;

import com.drgym.drgym.model.FriendshipInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendshipInvitationRepository extends JpaRepository<FriendshipInvitation, Long> {
    List<FriendshipInvitation> findByWhoReceiveUsername(String username);

    @Query("SELECT CASE WHEN COUNT(fi) > 0 THEN true ELSE false END FROM FriendshipInvitation fi WHERE fi.whoSendUsername = :sender AND fi.whoReceiveUsername = :receiver")
    boolean existsInvitation(@Param("sender") String sender, @Param("receiver") String receiver);
}
