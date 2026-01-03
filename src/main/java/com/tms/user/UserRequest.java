package com.tms.user;

public record UserRequest(
        String username,
        String email,
        String password,
        String phone,
        UserRole role
) {
}
