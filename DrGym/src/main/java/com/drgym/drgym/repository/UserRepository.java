package com.drgym.drgym.repository;

import com.drgym.drgym.model.UserTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserTemplate, String> {
}