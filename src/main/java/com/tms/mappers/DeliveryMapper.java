package com.tms.mappers;

import com.tms.dtos.DeliveryDetailedDto;
import com.tms.dtos.DeliveryDto;
import com.tms.dtos.DriverDto;
import com.tms.dtos.VehicleDto;
import com.tms.model.Delivery;

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
                .driver(DriverMapper.toDto(delivery.getDriver()))
                .history(delivery.getDeliveryHistory())
                .build();
    }
}
