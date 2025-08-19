package com.singh.ecommerceapp.processor;

import com.singh.ecommerceapp.dtos.CreatePermissionDto;
import com.singh.ecommerceapp.entity.Permission;
import com.singh.ecommerceapp.entity.Role;
import com.singh.ecommerceapp.repository.PermissionRepository;
import com.singh.ecommerceapp.repository.RoleRepository;
import com.singh.ecommerceapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class PermissionEntityProcessor implements EntityProcessor<CreatePermissionDto> {

    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void process(CreatePermissionDto createPermissionDto) {
       //log.info("createPermissionDto : {}", createPermissionDto);
        Permission permission = permissionRepository.findByName(createPermissionDto.getName())
                .orElseGet(() -> permissionRepository.save(createPermissionDto.toPermission()));
        assignToRoles(permission, createPermissionDto.getRoleNames());
    }

    private void assignToRoles(Permission permission, String[] roleNames) {
        Arrays.stream(roleNames).parallel().forEach(roleName -> {
            Optional<Role> role = roleService.findByName(roleName);
            role.ifPresentOrElse(
                    roleFound -> {
                        //log.info("Role {} already exists.", roleFound.getName());
                        if (!roleFound.hasPermission(permission.getName())) {
                            roleFound.addPermission(permission);
                            roleRepository.save(roleFound);
                            //log.info("Added permission '{}' to role '{}'", permission.getName(), roleName);
                        }
                    },
                    () -> log.warn("Role '{}' not found for permission '{}'", roleName, permission.getName())
            );
        });
    }
}
