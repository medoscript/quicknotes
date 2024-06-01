package com.example.quicknotes.exception_handling.exceptions;

public class FirstTestException extends RuntimeException {

    public FirstTestException(String message) {
        super(message);
    }

    public FirstTestException(String message, Throwable cause) {
        super(message, cause);
    }
}