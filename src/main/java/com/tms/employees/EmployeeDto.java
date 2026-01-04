package com.tms.employees;

import com.tms.employees.driver_profile.DriverProfileDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record EmployeeDto(
        UUID id,
        String username,
        String email,
        String phone,
        EmployeeRole role,
        DriverProfileDto driverProfile
) {
}
