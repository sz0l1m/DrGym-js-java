package com.drgym.drgym.repository;

import com.drgym.drgym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    List<User> findByUsernameContaining(String search);
    void deleteByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END FROM Friendship f WHERE (f.friend1Username = :username1 AND f.friend2Username = :username2) OR (f.friend1Username = :username2 AND f.friend2Username = :username1)")
    boolean areUsersFriends(@Param("username1") String username1, @Param("username2") String username2);
}