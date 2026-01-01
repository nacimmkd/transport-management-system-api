package com.tms.services.deliveryService.deliveryState.states;

import com.tms.model.Delivery;
import com.tms.services.deliveryService.deliveryState.State;

public class Failed extends State {
    public Failed(Delivery delivery) {
        super(delivery);
    }
}
