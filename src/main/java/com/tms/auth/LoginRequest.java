package com.tms.security;

public record LoginRequest(
        String email, String password
) {
}
