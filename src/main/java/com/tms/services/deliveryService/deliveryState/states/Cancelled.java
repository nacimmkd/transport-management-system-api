package com.tms.services.deliveryService.deliveryState.states;

import com.tms.model.Delivery;
import com.tms.services.deliveryService.deliveryState.State;

public class Cancelled extends State {
    public Cancelled(Delivery delivery) {
        super(delivery);
    }
}
