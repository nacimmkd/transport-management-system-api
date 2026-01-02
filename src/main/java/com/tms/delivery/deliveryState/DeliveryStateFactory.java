package com.tms.delivery.deliveryState;

import com.tms.delivery.Delivery;
import com.tms.delivery.deliveryState.states.*;

public class DeliveryStateFactory {

    public static IState from(Delivery delivery) {
        return switch (delivery.getStatus()) {
            case PENDING -> new Pending(delivery);
            case ASSIGNED -> new Assigned(delivery);
            case IN_TRANSIT -> new InTransit(delivery);
            case ARRIVED -> new Arrived(delivery);
            case DELIVERED -> new Delivered(delivery);
            case CANCELLED -> new Cancelled(delivery);
            case FAILED -> new Failed(delivery);
        };
    }
}
