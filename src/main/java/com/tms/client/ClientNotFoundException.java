package com.tms.client;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super("Client not found");
    }
}
