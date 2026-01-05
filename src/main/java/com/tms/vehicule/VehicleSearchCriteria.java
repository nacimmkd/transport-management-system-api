package com.tms.vehicule;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record VehicleSearchCriteria (
        VehicleStatus status,
        VehicleType type,
        String plateNumber,
        BigDecimal minCapacityKg,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime availableAt
) {

}
