package com.singh.ecommerceapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Table(name = "roles")
@Entity
@ToString
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    private boolean isDefault;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>(); // Initialize to avoid NullPointerException

    public void addPermission(Permission permission) {
        if (this.permissions == null) {
            this.permissions = new HashSet<>(); // Ensure permissions is not null
        }
        this.permissions.add(permission);
    }

    public boolean hasPermission(String permissionName) {
        if (this.permissions == null) {
            return false; // If permissions is null, return false
        }
        return this.permissions.stream()
                .anyMatch(permission -> permission.getName().equals(permissionName));
    }

    public void removePermission(Permission permission) {
        if (this.permissions != null) {
            this.permissions = this.permissions.stream()
                    .filter(existingPermission -> !existingPermission.getName().equals(permission.getName()))
                    .collect(Collectors.toSet());
        }
    }
}