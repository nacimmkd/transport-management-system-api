package com.tms.employees.driver;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DriverProfileDto(
        String licenseNumber,
        LicenseCategory licenseCategory,
        LocalDate licenseExpiryDate
) {
}
