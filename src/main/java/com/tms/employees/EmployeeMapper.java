package com.tms.employees;

import com.tms.company.Company;
import com.tms.employees.driverProfile.DriverProfileMapper;

public class EmployeeMapper {

    public static EmployeeDto toDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .email(employee.getEmail().toLowerCase())
                .phone(employee.getPhone())
                .role(employee.getRole())
                .driverProfile(employee.getDriverProfile() != null
                        ? DriverProfileMapper.toDto(employee.getDriverProfile())
                        : null)
                .build();
    }


    public static Employee toEntity(EmployeeRegisterRequest request, Company company) {
        return Employee.builder()
                .username(request.username())
                .email(request.email().toLowerCase())
                .phone(request.phone())
                .role(EmployeeRole.valueOf(request.role().name()))
                .company(company)
                .build();
    }

    public static Employee toEntity(EmployeeUpdateRequest request, Company company) {
        return Employee.builder()
                .username(request.username())
                .email(request.email().toLowerCase())
                .phone(request.phone())
                .password(request.password())
                .company(company)
                .build();
    }

}
