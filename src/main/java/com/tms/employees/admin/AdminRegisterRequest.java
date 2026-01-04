package com.tms.employees.admin;

import com.tms.employees.EmployeeRole;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AdminRegisterRequest(
        UUID id,
        String username,
        String email,
        String password,
        String phone
) {
}
