package com.tms.delivery;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record DeliveryDto(
        UUID id,
        String pickupAddress,
        String deliveryAddress,
        DeliveryStatus status,
        LocalDateTime requestedDeliveryTime,
        LocalDateTime plannedStartTime)
{}
