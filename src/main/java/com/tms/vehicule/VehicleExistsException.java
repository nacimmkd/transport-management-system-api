package com.tms.vehicule;

public class VehicleExistsException extends RuntimeException {
    public VehicleExistsException() {
        super("Vehicle with same plate number already exists");
    }
}
