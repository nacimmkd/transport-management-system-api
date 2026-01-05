package com.tms.employees;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record EmployeeSearchCriteria(
        String username,
        String email,
        String phone,
        EmployeeAllowedRoles role,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime availableAt
) {
}
