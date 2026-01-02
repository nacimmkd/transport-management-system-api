package com.tms.delivery.deliveryState.states;

import com.tms.delivery.Delivery;
import com.tms.delivery.DeliveryStatus;
import com.tms.delivery.deliveryState.State;

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
