package com.singh.ecommerceapp.service;

import com.singh.ecommerceapp.dtos.CreateRoleDto;
import com.singh.ecommerceapp.entity.Role;
import com.singh.ecommerceapp.entity.RoleEnum;
import com.singh.ecommerceapp.exceptions.ResourceNotFoundException;
import com.singh.ecommerceapp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role save(CreateRoleDto createRoleDto) {
        return roleRepository.save(createRoleDto.toRole());
    }

    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }

    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        roleRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
    public Optional<Role> findByName(String name)  {
        return roleRepository.findByName(RoleEnum.valueOf(name));
    }

    public Role findById(Integer id) throws ResourceNotFoundException {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isEmpty()) {
            throw new ResourceNotFoundException("role not found");
        }
        return roleOptional.get();
    }

    public Role update(Integer id, CreateRoleDto createRoleDto) throws ResourceNotFoundException {
        Role roleToUpdate = findById(id);

        roleToUpdate.setName(RoleEnum.valueOf(createRoleDto.getName())).setDescription(createRoleDto.getDescription());

        return roleRepository.save(roleToUpdate);
    }

    public Role update(Role role) {
        return roleRepository.save(role);
    }
}
