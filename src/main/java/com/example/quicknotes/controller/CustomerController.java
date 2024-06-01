package com.example.quicknotes.controller;


import com.example.quicknotes.domain.entity.Customer;
import com.example.quicknotes.service.interfaces.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public Customer getById(@RequestParam Long id) {
        return service.getById(id);
    }

    @GetMapping("/all")
    public List<Customer> getAll() {
        return service.getAll();
    }
}