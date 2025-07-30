package com.singh.ecommerceapp.security;

import com.singh.ecommerceapp.security.oauth2.OAuth2Provider;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements OAuth2User, UserDetails {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String avatarUrl;
    private OAuth2Provider provider;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
}
