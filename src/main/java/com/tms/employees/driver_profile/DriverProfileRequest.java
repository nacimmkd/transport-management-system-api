package com.tms.employees.driver_profile;

import java.time.LocalDate;

public record DriverProfileRequest(
        String licenseNumber,
        LicenseType licenseCategory,
        LocalDate licenseExpiryDate
) {
}
