package com.tms.employees;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EmployeeDto(
        UUID id,
        String username,
        String email,
        String phone,
        EmployeeRole role
) {
}
