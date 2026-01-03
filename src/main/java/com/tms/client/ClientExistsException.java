package com.tms.client;

public class ClientExistsException extends RuntimeException {
    public ClientExistsException() {
        super("Client with same email exists");
    }
}
