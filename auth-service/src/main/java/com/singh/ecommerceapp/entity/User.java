package com.singh.ecommerceapp.entity;

import com.singh.ecommerceapp.security.oauth2.OAuth2Provider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@ToString
@Accessors(chain = true)
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String name;
    private String email;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;
    private String providerId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @Version

    @Column(name = "VERSION")
    private Long version;  // Version field for optimistic locking

    public Set<Permission> allPermissions() {
        Set<Permission> userRolePermissions = this.role.getPermissions();
        return new HashSet<>(userRolePermissions);
    }
}