package com.drgym.drgym.repository;

import com.drgym.drgym.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, String> {
    @Query("SELECT t FROM Token t WHERE t.email = :email AND t.verificationToken = :token")
    Token findByEmailAndVerificationToken(@Param("email") String email, @Param("token") String token);

    @Query("SELECT t FROM Token t WHERE t.email = :email AND t.resetToken = :token AND t.resetTokenExpiry > CURRENT_TIMESTAMP")
    Token findByEmailAndResetToken(@Param("email") String email, @Param("token") String token);

    void deleteAllByEmail(String email);
}