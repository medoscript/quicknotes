package com.example.quicknotes.service.interfaces;


import com.example.quicknotes.domain.entity.User;

public interface EmailService {

    void sendConfirmationEmail(User user);
}