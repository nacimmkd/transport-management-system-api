package com.tms.services.deliveryService.deliveryState.states;

import com.tms.model.Delivery;
import com.tms.model.enums.DeliveryStatus;
import com.tms.services.deliveryService.deliveryState.State;

public class Arrived extends State {
    public Arrived(Delivery delivery) {
        super(delivery);
    }

    @Override
    public void confirm(){
        delivery.setStatus(DeliveryStatus.DELIVERED);
    }

    @Override
    public void fail(){
        delivery.setStatus(DeliveryStatus.FAILED);
    }
}
