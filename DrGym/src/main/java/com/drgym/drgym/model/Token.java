package com.drgym.drgym.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Token")
public class Token {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "verification_token", nullable = true)
    private String verificationToken;

    @Column(name = "reset_token", nullable = true)
    private String resetToken;

    @Column(name = "reset_expiry", nullable = true)
    private Date resetTokenExpiry;

    public Token() {}

    public Token(String email, String verificationToken) {
        this.email = email;
        this.verificationToken = verificationToken;
        this.resetToken = null;
        this.resetTokenExpiry = null;
    }

    public Token(String email, String verificationToken, String resetToken, Date resetTokenExpiry) {
        this.email = email;
        this.verificationToken = verificationToken;
        this.resetToken = resetToken;
        this.resetTokenExpiry = resetTokenExpiry;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Date getResetTokenExpiry() {
        return resetTokenExpiry;
    }

    public void setResetTokenExpiry(Date resetTokenExpiry) {
        this.resetTokenExpiry = resetTokenExpiry;
    }
}