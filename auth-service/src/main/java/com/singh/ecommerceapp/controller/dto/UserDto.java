package com.singh.ecommerceapp.controller.dto;


import com.singh.ecommerceapp.entity.Role;
import com.singh.ecommerceapp.entity.User;

public record UserDto(Long id, String username, String name, String email, Role role) {

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}

