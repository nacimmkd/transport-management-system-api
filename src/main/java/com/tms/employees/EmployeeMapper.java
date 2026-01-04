package com.tms.employees;

import com.tms.company.Company;

public class EmployeeMapper {

    public static EmployeeDto toDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .email(employee.getEmail().toLowerCase())
                .phone(employee.getPhone())
                .role(employee.getRole())
                .build();
    }

    public static Employee toEntity(EmployeeRegisterRequest request, Company company) {
        return Employee.builder()
                .username(request.username())
                .email(request.email().toLowerCase())
                .password(request.password())
                .phone(request.phone())
                .role(EmployeeRole.valueOf(request.role().name()))
                .company(company)
                .build();
    }
}
