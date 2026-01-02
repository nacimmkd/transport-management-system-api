package com.tms.client;

public class ClientDeletedException extends RuntimeException {
    public ClientDeletedException() {
        super("Client was deleted");
    }
}
