package com.example.quicknotes.service;


import com.example.quicknotes.domain.entity.Customer;
import com.example.quicknotes.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public Customer getById(Long id) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
        return null;
    }

    @Override
    public List<Customer> getAll() {
        System.out.println("**************************");
        return null;
    }
}