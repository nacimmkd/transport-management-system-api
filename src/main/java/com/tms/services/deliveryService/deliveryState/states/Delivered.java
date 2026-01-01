package com.tms.services.deliveryService.deliveryState.states;

import com.tms.model.Delivery;
import com.tms.services.deliveryService.deliveryState.State;

public class Delivered extends State {
    public Delivered(Delivery delivery) {
        super(delivery);
    }

}
