package com.tms.vehicule;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record VehicleDto(
        UUID id,
        String brand,
        String model,
        String plateNumber,
        VehicleType type,
        BigDecimal capacityKg,
        VehicleStatus status
) {
}
