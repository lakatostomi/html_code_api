package com.example.demo.restapi.exception;

public class ConstructorInvocationException extends RuntimeException {

    public ConstructorInvocationException() {
    }

    public ConstructorInvocationException(String message) {
        super(message);
    }
}
