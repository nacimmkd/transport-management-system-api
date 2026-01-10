package com.tms.company;

public record CompanyRegistrationRequest(
        String ownerUsername,
        String ownerEmail,
        String ownerPassword,
        String ownerPhone,
        CompanyRegisterRequest company
) {
}
