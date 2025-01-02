package com.drgym.drgym.repository;

import com.drgym.drgym.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Token t WHERE t.email = :email AND t.token = :token")
    boolean isValidToken(@Param("email") String email, @Param("token") String token);
}