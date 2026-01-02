package com.tms.delivery.deliveryState.states;

import com.tms.delivery.Delivery;
import com.tms.delivery.DeliveryStatus;
import com.tms.delivery.deliveryState.State;

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
