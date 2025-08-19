package com.singh.ecommerceapp.service;

import com.singh.ecommerceapp.controller.dto.SignUpRequest;
import com.singh.ecommerceapp.entity.Role;
import com.singh.ecommerceapp.entity.RoleEnum;
import com.singh.ecommerceapp.entity.User;
import com.singh.ecommerceapp.exceptions.UserNotFoundException;
import com.singh.ecommerceapp.repository.RoleRepository;
import com.singh.ecommerceapp.repository.UserRepository;
import com.singh.ecommerceapp.security.oauth2.OAuth2Provider;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User validateAndGetUserByUsername(String username) {
        return getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
    }

    @Override
    public User saveUser(SignUpRequest signUpRequest) {

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ROLE_USER);
        if (optionalRole.isEmpty() || !"ROLE_USER".equals(optionalRole.get().getName().name())) {
            throw new IllegalArgumentException("Only 'ROLE_USER' role is allowed for registration.");
        }
        User user = mapSignUpRequestToUser(signUpRequest);

        user.setRole(optionalRole.get());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.username());
        user.setFirstName(signUpRequest.firstName());
        user.setLastName(signUpRequest.lastName());
        user.setEmail(signUpRequest.email());
        user.setProvider(OAuth2Provider.LOCAL);
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setProviderId("1");
        return user;
    }
}
