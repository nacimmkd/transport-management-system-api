package com.tms.services.deliveryService.exceptions;

public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException() {
        super("Delivery Not Found");
    }
}
