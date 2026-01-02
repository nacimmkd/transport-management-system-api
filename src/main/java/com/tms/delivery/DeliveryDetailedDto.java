package com.tms.delivery;

import com.tms.client.ClientDto;
import com.tms.driver.DriverDto;
import com.tms.vehicule.VehicleDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record DeliveryDetailedDto(
        UUID id,
        String deliveryCode,
        String pickupAddress,
        String deliveryAddress,
        DeliveryStatus status,
        BigDecimal weightKg,
        LocalDateTime requestedDeliveryTime,
        LocalDateTime plannedStartTime,
        LocalDateTime deliveredAt,
        BigDecimal price,
        ClientDto client,
        VehicleDto vehicle,
        DriverDto driver,
        List<DeliveryHistory> history)
{}
