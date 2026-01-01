package com.tms.dtos;

import com.tms.model.enums.DriverLicenseType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record DriverDto(
        UUID id,
        String username,
        String email,
        String phone,
        String licenseNumber,
        DriverLicenseType licenseCategory
) {
}
