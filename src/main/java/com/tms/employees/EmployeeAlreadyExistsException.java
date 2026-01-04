package com.tms.employees;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException() {
        super("Email already exists!");
    }
}
