package com.drgym.drgym.repository;

import com.drgym.drgym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}