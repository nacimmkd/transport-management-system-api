package com.tms.exceptions;

public class ClientDeletedException extends RuntimeException {
    public ClientDeletedException() {
        super("Client was deleted");
    }
}
