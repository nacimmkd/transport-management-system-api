package com.tms.delivery.deliveryState.states;

import com.tms.delivery.Delivery;
import com.tms.delivery.DeliveryStatus;
import com.tms.delivery.deliveryState.State;

public class Pending extends State {

    public Pending(Delivery delivery) {
        super(delivery);
    }

    @Override
    public void assignToDriver() {
        delivery.setStatus(DeliveryStatus.ASSIGNED);
    }

    @Override
    public void cancel() {
        delivery.setStatus(DeliveryStatus.CANCELLED);
        //delivery.setDriver(null);
        //delivery.setVehicle(null);
    }
}
