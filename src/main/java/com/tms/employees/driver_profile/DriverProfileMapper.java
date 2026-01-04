package com.tms.employees.driver_profile;

public class DriverProfileMapper {

    public static DriverProfileDto toDto(DriverProfile driver) {
        if (driver == null) {
            return null;
        }
        return DriverProfileDto.builder()
                .licenseNumber(driver.getLicenseNumber())
                .licenseCategory(driver.getLicenseCategory())
                .licenseExpiryDate(driver.getLicenseNumber())
                .build();
    }

    public static DriverProfile toEntity(DriverProfileRequest driverRequest) {
        return DriverProfile.builder()
                .licenseNumber(driverRequest.licenseNumber())
                .licenseCategory(driverRequest.licenseCategory())
                .licenseExpiryDate(driverRequest.licenseExpiryDate())
                .build();
    }
}
