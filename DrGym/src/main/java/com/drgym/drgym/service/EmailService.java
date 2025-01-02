package com.drgym.drgym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("appdrgym@gmail.com");
        message.setTo(to);
        message.setSubject("Email Verification");
        message.setText("Click the following link to verify your email: " + link);
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String to, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("appdrgym@gmail.com");
        message.setTo(to);
        message.setSubject("Password Reset");
        message.setText("Click the following link to reset your password: " + link);
        mailSender.send(message);
    }
}