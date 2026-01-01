package com.tms.dtos;

import com.tms.model.enums.VehicleStatus;
import com.tms.model.enums.VehicleType;
import lombok.Builder;
import lombok.Data;

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
