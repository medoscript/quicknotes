package com.example.quicknotes.exception_handling.exceptions;

public class ProductNotFoundException  extends RuntimeException{

    public ProductNotFoundException(String taskTitle){
        super(String.format("Task with title %s not found"));
    }
}
