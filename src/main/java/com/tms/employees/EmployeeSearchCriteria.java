package com.tms.employees;

public record EmployeeSearchCriteria(
        String username,
        String email,
        String phone,
        EmployeeAllowedRoles role
) {
}
