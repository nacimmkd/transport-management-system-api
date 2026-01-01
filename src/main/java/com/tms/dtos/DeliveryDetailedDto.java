package com.tms.dtos;

import com.tms.model.DeliveryHistory;
import com.tms.model.enums.DeliveryStatus;
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
