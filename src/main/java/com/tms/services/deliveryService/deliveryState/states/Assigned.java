package com.tms.services.deliveryService.deliveryState.states;

import com.tms.model.Delivery;
import com.tms.model.enums.DeliveryStatus;
import com.tms.services.deliveryService.deliveryState.State;

public class Assigned extends State {
    public Assigned(Delivery delivery) {
        super(delivery);
    }

    @Override
    public void transit(){
        delivery.setStatus(DeliveryStatus.IN_TRANSIT);
    }

    @Override
    public void cancel(){
        delivery.setStatus(DeliveryStatus.CANCELLED);
    }
}
