package com.drgym.drgym.repository;

import com.drgym.drgym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findByName(String name);
    User findBySurname(String surname);
    User findByEmail(String email);
    User save(User user);
}