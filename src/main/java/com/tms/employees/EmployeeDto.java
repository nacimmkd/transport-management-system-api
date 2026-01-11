package com.tms.employees;

import com.tms.company.CompanyDto;
import com.tms.employees.driver.DriverProfileDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record EmployeeDto(
        UUID id,
        String username,
        String email,
        String phone,
        EmployeeRole role,
        DriverProfileDto driverProfile,
        CompanyDto company
) {
}
