package com.tms.employees.driverProfile;

import java.time.LocalDateTime;

public record DriverProfileRequest(
        String licenseNumber,
        LicenseType licenseCategory,
        LocalDateTime licenseExpiryDate
) {
}
