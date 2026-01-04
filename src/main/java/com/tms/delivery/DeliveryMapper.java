package com.tms.delivery;

import com.tms.client.ClientMapper;
import com.tms.employees.driver_profile.DriverProfileMapper;
import com.tms.vehicule.VehicleMapper;

public class DeliveryMapper {

    public static DeliveryDto toDto(Delivery delivery ) {
        return DeliveryDto.builder()
                .id(delivery.getId())
                .deliveryAddress(delivery.getDeliveryAddress())
                .pickupAddress(delivery.getPickupAddress())
                .status(delivery.getStatus())
                .requestedDeliveryTime(delivery.getRequestedDeliveryTime())
                .plannedStartTime(delivery.getPlannedStartTime())
                .build();

    }

    public static DeliveryDetailedDto toDetailedDto(Delivery delivery) {
        return DeliveryDetailedDto.builder()
                .id(delivery.getId())
                .pickupAddress(delivery.getPickupAddress())
                .deliveryAddress(delivery.getDeliveryAddress())
                .deliveryCode(delivery.getDeliveryCode())
                .status(delivery.getStatus())
                .weightKg(delivery.getWeightKg())
                .requestedDeliveryTime(delivery.getRequestedDeliveryTime())
                .plannedStartTime(delivery.getPlannedStartTime())
                .deliveredAt(delivery.getDeliveredAt())
                .price(delivery.getPrice())
                .client(ClientMapper.toDto(delivery.getClient()))
                .vehicle(VehicleMapper.toDto(delivery.getVehicle()))
                .driver(DriverProfileMapper.toDto(delivery.getDriver()))
                .history(delivery.getDeliveryHistory())
                .build();
    }
}
