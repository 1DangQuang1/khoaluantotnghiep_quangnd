package com.example.restapi.exceptions;

public class DuplicateCccdException extends RuntimeException {
    public DuplicateCccdException(String message) {
        super(message);
    }
}