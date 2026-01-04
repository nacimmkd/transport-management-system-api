package com.tms.employees;

public record EmployeeRegisterRequest(
        String username,
        String email,
        String password,
        String phone,
        EmployeeAllowedRoles role
) {
}
