package com.tms.employees;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tms.employees.driver.LicenseCategory;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EmployeeSearchCriteria(
        String username,
        String email,
        String phone,
        EmployeeAllowedRoles role,
        String licenseNumber,
        LicenseCategory licenseCategory,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime availableAt
) {
}
