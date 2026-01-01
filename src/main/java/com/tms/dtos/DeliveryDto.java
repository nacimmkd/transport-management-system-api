package com.tms.dtos;

import com.tms.model.Client;
import com.tms.model.Driver;
import com.tms.model.Vehicle;
import com.tms.model.enums.DeliveryStatus;
import lombok.Builder;

import java.math.BigDecimal;
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
