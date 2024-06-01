package com.example.quicknotes.exception_handling;


import com.example.quicknotes.exception_handling.exceptions.FourthTestException;
import com.example.quicknotes.exception_handling.exceptions.ProductNotFoundException;
import com.example.quicknotes.exception_handling.exceptions.ThirdTestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ThirdTestException.class)
    public ResponseEntity<Response> handleException(ThirdTestException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response> handleException(ProductNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FourthTestException.class)
    public ResponseEntity<Response> handleException(FourthTestException e) {
        Throwable childException = e.getCause();
        Response response = new Response(e.getMessage(),
                childException == null ? null : parseExceptionMessage(childException.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.I_AM_A_TEAPOT);
    }

    private String parseExceptionMessage(String message) {
        // При желании здесь можно реализовать логику, которая из сообщения
        // выделяет только нужную информацию
        return message;
    }
}