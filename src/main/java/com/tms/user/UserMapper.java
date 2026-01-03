package com.tms.user;

import com.tms.company.Company;

public class UserMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail().toLowerCase())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    public static User toEntity(UserRegisterRequest userRequest, Company company) {
        return User.builder()
                .username(userRequest.username())
                .email(userRequest.email().toLowerCase())
                .password(userRequest.password())
                .phone(userRequest.phone())
                .role(UserRole.valueOf(userRequest.role().name()))
                .company(company)
                .build();
    }
}
