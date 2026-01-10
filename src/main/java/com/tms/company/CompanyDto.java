package com.tms.company;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CompanyDto(
        UUID id,
        String name,
        String siren,
        String address,
        String email,
        String phone
) {}
