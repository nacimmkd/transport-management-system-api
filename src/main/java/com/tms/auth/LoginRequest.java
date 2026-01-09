package com.tms.auth;

public record LoginRequest(
        String email, String password
) {
}
