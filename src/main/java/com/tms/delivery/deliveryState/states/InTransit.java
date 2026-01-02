package com.tms.delivery.deliveryState.states;

import com.tms.delivery.Delivery;
import com.tms.delivery.DeliveryStatus;
import com.tms.delivery.deliveryState.State;

public class InTransit extends State {
    public InTransit(Delivery delivery) {
        super(delivery);
    }

    @Override
    public void arrive() {
        delivery.setStatus(DeliveryStatus.ARRIVED);
    }
}
