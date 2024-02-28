package com.example.demo.restapi.exception;

public class RequiredArgumentMissingException extends RuntimeException {

    public RequiredArgumentMissingException() {
    }

    public RequiredArgumentMissingException(String message) {
        super(message);
    }
}
