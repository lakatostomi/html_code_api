package com.example.demo.restapi.exception;

public class UnsupportedElementException extends RuntimeException {

    public UnsupportedElementException() {
    }

    public UnsupportedElementException(String message) {
        super(message);
    }
}
