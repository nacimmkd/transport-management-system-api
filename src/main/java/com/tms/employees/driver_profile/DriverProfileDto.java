package com.tms.employees.driver_profile;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DriverProfileDto(
        String licenseNumber,
        LicenseType licenseCategory,
        LocalDate licenseExpiryDate
) {
}
