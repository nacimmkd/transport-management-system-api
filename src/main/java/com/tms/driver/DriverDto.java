package com.tms.driver;

import lombok.Builder;

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
