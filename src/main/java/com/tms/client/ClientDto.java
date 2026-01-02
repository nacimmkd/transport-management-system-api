package com.tms.client;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ClientDto(
        UUID id,
        String name,
        String phone,
        String email,
        String address
) {}

