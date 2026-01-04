package com.tms.employees.driverProfile;

import lombok.Builder;

@Builder
public record DriverProfileDto(
        String licenseNumber,
        LicenseType licenseCategory,
        String licenseExpiryDate
) {
}
