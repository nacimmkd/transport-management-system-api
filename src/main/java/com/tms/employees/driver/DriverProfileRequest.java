package com.tms.employees.driver;

import java.time.LocalDate;

public record DriverProfileRequest(
        String licenseNumber,
        LicenseCategory licenseCategory,
        LocalDate licenseExpiryDate
) {
}
