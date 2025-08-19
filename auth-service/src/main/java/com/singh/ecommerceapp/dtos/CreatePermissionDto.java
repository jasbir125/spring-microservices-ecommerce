package com.singh.ecommerceapp.dtos;

import com.singh.ecommerceapp.entity.Permission;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreatePermissionDto {

    private String name;
    private String description;
    private String[] roleNames;

    public Permission toPermission() {
        return new Permission(name, description);
    }
}
