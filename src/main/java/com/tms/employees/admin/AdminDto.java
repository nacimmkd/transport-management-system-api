package com.tms.employees.admin;

import com.tms.employees.EmployeeRole;
import lombok.Builder;
import java.util.UUID;

@Builder
public record AdminDto(
        UUID id,
        String username,
        String email,
        String phone,
        EmployeeRole role
) {}
