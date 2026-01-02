package com.tms.delivery;

public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException() {
        super("Delivery Not Found");
    }
}
