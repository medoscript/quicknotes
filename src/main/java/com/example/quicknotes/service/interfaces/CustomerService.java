package com.example.quicknotes.service.interfaces;


import com.example.quicknotes.domain.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer getById(Long id);

    List<Customer> getAll();
}