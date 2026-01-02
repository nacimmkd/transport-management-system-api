package com.tms.vehicule;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record VehicleRequest(
        String brand,
        String model,
        String plateNumber,
        VehicleType type,
        BigDecimal capacityKg
)
{}
