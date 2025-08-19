package com.singh.ecommerceapp.processor;

import com.singh.ecommerceapp.dtos.CreateRoleDto;
import com.singh.ecommerceapp.entity.Role;
import com.singh.ecommerceapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoleEntityProcessor implements EntityProcessor<CreateRoleDto> {

    private final RoleService roleService;

    @Override
    public void process(CreateRoleDto createRoleDto) {
        Optional<Role> optionalRole = roleService.findByName(createRoleDto.getName());
        optionalRole.ifPresentOrElse(
                role -> log.info("Role {} already exists.", role.getName()),
                () -> {
                    CreateRoleDto roleToCreate = new CreateRoleDto();
                    roleToCreate.setName(createRoleDto.getName())
                            .setDescription(createRoleDto.getDescription())
                            .setDefault(true);
                    roleService.save(roleToCreate);
                    //log.info("Role {} has been created.", roleToCreate.getName());
                }
        );
    }
}