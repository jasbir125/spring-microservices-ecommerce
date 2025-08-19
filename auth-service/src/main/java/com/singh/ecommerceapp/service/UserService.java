package com.singh.ecommerceapp.service;

import com.singh.ecommerceapp.controller.dto.SignUpRequest;
import com.singh.ecommerceapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers();

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);

    User saveUser(SignUpRequest signUpRequest);

    void deleteUser(User user);
}
