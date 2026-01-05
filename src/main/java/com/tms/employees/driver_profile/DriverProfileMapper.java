package com.tms.employees.driver_profile;

import java.time.LocalDate;

public class DriverProfileMapper {

    public static DriverProfileDto toDto(DriverProfile driver) {
        if (driver == null) {
            return null;
        }
        return DriverProfileDto.builder()
                .licenseNumber(driver.getLicenseNumber().toUpperCase())
                .licenseCategory(driver.getLicenseCategory())
                .licenseExpiryDate(driver.getLicenseExpiryDate())
                .build();
    }

    public static DriverProfile toEntity(DriverProfileRequest driverRequest) {
        return DriverProfile.builder()
                .licenseNumber(driverRequest.licenseNumber().toUpperCase())
                .licenseCategory(driverRequest.licenseCategory())
                .licenseExpiryDate(driverRequest.licenseExpiryDate())
                .build();
    }
}
