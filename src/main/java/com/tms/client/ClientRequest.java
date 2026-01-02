package com.tms.client;

import lombok.Builder;

@Builder
public record ClientRequest(
        String name,
        String phone,
        String email,
        String address
)
{}
