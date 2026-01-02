package com.tms.vehicule;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VehicleSearchCriteria {
    private VehicleStatus status;
    private VehicleType type;
    private String plateNumber;
    private BigDecimal minCapacityKg;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime availableAt;
}
