package com.drgym.drgym.repository;

import com.drgym.drgym.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByFriend1UsernameOrFriend2Username(String friend1, String friend2);
    boolean existsByFriend1UsernameAndFriend2Username(String friend1, String friend2);
    void deleteByFriend1UsernameAndFriend2Username(String friend1, String friend2);
}