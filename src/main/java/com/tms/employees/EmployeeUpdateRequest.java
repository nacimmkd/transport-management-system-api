package com.tms.employees;

import com.tms.employees.driverProfile.DriverProfileRequest;

public record EmployeeUpdateRequest(
        String username,
        String email,
        String password,
        String phone,
        DriverProfileRequest driverProfile
) {
}