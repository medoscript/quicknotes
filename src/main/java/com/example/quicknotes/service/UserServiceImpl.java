package com.example.quicknotes.service;

import com.example.quicknotes.domain.entity.User;
import com.example.quicknotes.repo.UserRepository;
import com.example.quicknotes.service.interfaces.EmailService;
import com.example.quicknotes.service.interfaces.RoleService;
import com.example.quicknotes.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private RoleService roleService;
    private BCryptPasswordEncoder encoder;
    private EmailService emailService;

    public UserServiceImpl(UserRepository repository, RoleService roleService, BCryptPasswordEncoder encoder, EmailService emailService) {
        this.repository = repository;
        this.roleService = roleService;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;


    }
    @Override
    public User register(User user){
        user.setId(null);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(false);
        user.setRoles(Set.of(roleService.getRoleUser()));

        repository.save(user);
        emailService.sendConfirmationEmail(user);
        return user;
    }
}