package com.tms.company;

import lombok.Builder;

@Builder
public record CompanyRegistrationResponse(
        String ownerUsername,
        String ownerEmail,
        String ownerPhone,
        CompanyDto company
) {
}
