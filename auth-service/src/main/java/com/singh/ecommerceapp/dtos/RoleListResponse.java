package com.singh.ecommerceapp.dtos;

import com.singh.ecommerceapp.entity.Permission;
import com.singh.ecommerceapp.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Data
public class RoleListResponse {
    private List<Role> data;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class PermissionLoadDto {
        private String name;

        private String description;

        private String[] roleNames;

        public Permission toPermission() {
            return new Permission(name, description);
        }
    }

    public enum PermissionLoadMode {
        CREATE("create"), UPDATE("update");

        final String value;

        PermissionLoadMode(String value) {
            this.value = value;
        }
    }
}
