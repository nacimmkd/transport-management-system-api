package com.tms.mappers;

import com.tms.dtos.UserDto;
import com.tms.model.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();

    }
}
