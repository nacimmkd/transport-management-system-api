package com.tms.services.deliveryService.deliveryState.states;

import com.tms.model.Delivery;
import com.tms.model.enums.DeliveryStatus;
import com.tms.services.deliveryService.deliveryState.State;

public class InTransit extends State {
    public InTransit(Delivery delivery) {
        super(delivery);
    }

    @Override
    public void arrive() {
        delivery.setStatus(DeliveryStatus.ARRIVED);
    }
}
