package com.example.restapi.exceptions;

public class VisitNotFoundException extends RuntimeException {
    public VisitNotFoundException(String message) {
        super(message);
    }
}
