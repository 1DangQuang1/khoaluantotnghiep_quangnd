package com.example.restapi.exceptions;

public class DepartmentNotFoundException extends IllegalArgumentException{
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
