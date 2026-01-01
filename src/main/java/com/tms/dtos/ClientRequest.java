package com.tms.dtos;

import lombok.Builder;

@Builder
public record ClientRequest(
        String name,
        String phone,
        String email,
        String address
)
{}
