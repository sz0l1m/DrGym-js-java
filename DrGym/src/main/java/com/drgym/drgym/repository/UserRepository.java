package com.drgym.drgym.repository;

import com.drgym.drgym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Transactional
    default void verifyEmail(String email) {
        Optional<User> user = findByEmail(email);
        user.ifPresent(u -> {
            u.setVerified(true);
            save(u);
        });
    }

    @Transactional
    default void resetPassword(String email, String newPassword) {
        Optional<User> user = findByEmail(email);
        user.ifPresent(u -> {
            u.setPassword(newPassword);
            save(u);
        });
    }
}