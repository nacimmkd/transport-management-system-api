package com.tms.company;

public class CompanyMapper {

    public static CompanyDto toDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .email(company.getEmail())
                .siren(company.getSiren())
                .address(company.getAddress())
                .build();
    }
}
