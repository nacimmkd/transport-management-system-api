package com.tms.company;

public class CompanyAlreadyExistsException extends RuntimeException {
    public CompanyAlreadyExistsException() {
        super("Company already exists");
    }
}
