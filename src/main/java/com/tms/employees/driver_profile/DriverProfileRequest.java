package com.tms.employees.driver_profile;

import java.time.LocalDateTime;

public record DriverProfileRequest(
        String licenseNumber,
        LicenseType licenseCategory,
        LocalDateTime licenseExpiryDate
) {
}
