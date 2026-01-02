package com.tms.driver;

public class DriverMapper {

    public static DriverDto toDto(Driver driver) {
        return DriverDto.builder()
                .id(driver.getId())
                .username(driver.getUser().getUsername())
                .email(driver.getUser().getEmail())
                .phone(driver.getUser().getPhone())
                .licenseNumber(driver.getLicenseNumber())
                .licenseCategory(driver.getLicenseCategory())
                .build();
    }
}
