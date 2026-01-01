package com.tms.services.deliveryService.deliveryState.states;

import com.tms.model.Delivery;
import com.tms.model.enums.DeliveryStatus;
import com.tms.services.deliveryService.deliveryState.State;

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
