package com.drgym.drgym.repository;

import com.drgym.drgym.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByFriend1UsernameOrFriend2Username(String friend1, String friend2);
    boolean existsByFriend1UsernameAndFriend2Username(String friend1, String friend2);
    void deleteByFriend1UsernameAndFriend2Username(String friend1, String friend2);
    @Query("SELECT f.friend2Username FROM Friendship f WHERE f.friend1Username = :username " +
            "UNION SELECT f.friend1Username FROM Friendship f WHERE f.friend2Username = :username")
    List<String> findFriendsUsernamesByUsername(String username);
}