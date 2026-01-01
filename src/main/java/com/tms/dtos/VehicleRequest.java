package com.tms.dtos;

import com.tms.model.enums.VehicleStatus;
import com.tms.model.enums.VehicleType;
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
