package com.tms.employees.driver_profile;

import lombok.Builder;

@Builder
public record DriverProfileDto(
        String licenseNumber,
        LicenseType licenseCategory,
        String licenseExpiryDate
) {
}
