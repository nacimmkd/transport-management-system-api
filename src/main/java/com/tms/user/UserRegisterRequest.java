package com.tms.user;

public record UserRegisterRequest(
        String username,
        String email,
        String password,
        String phone,
        UserAllowedRoles role
) {
}
