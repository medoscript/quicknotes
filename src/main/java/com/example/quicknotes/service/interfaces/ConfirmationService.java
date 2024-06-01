package com.example.quicknotes.service.interfaces;


import com.example.quicknotes.domain.entity.User;

public interface ConfirmationService {

    String generateConfirmationCode(User user);
}