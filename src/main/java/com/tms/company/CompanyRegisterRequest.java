package com.tms.company;

public record CompanyRegisterRequest(
        String name,
        String siren,
        String address,
        String email,
        String phone
) {
}