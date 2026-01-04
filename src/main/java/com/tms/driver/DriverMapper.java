package com.tms.driver;

public class DriverMapper {

    public static DriverDto toDto(Driver driver) {
        return DriverDto.builder()
                .id(driver.getId())
                .username(driver.getEmployee().getUsername())
                .email(driver.getEmployee().getEmail())
                .phone(driver.getEmployee().getPhone())
                .licenseNumber(driver.getLicenseNumber())
                .licenseCategory(driver.getLicenseCategory())
                .build();
    }
}
