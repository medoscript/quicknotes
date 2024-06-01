package com.example.quicknotes.controller;


import com.example.quicknotes.domain.entity.User;
import com.example.quicknotes.service.interfaces.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/register")
public class RegistrationController {

    private UserService service;

    public RegistrationController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User register(@RequestBody User user){
        return service.register(user);
    }
}