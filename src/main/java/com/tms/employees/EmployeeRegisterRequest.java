package com.tms.employees;

import com.tms.employees.driver_profile.DriverProfileRequest;
import io.swagger.v3.oas.annotations.media.Schema;

public record EmployeeRegisterRequest(
        String username,
        String email,
        String phone,
        EmployeeAllowedRoles role,

        @Schema(
                description = "Informations sur le permis. OBLIGATOIRE si role=ROLE_DRIVER. Doit être laissé VIDE ou NULL si role=ROLE_MANAGER.",
                nullable = true
        )
        DriverProfileRequest driverProfile
) {
}
