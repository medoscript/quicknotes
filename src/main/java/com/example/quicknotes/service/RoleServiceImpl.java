package com.example.quicknotes.service;


import com.example.quicknotes.domain.entity.Role;
import com.example.quicknotes.repo.RoleRepository;
import com.example.quicknotes.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

@Service
public  class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleUser() {
        Role userRole = roleRepository.findByTitle("ROLE_USER");

        if (userRole == null){
            throw new RuntimeException("Database does not contain ROLE_USER");
        }

        return userRole;

    }
}