package com.singh.ecommerceapp.service;

import com.singh.ecommerceapp.entity.Permission;
import com.singh.ecommerceapp.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public List<Permission> findAll() {
        List<Permission> list = new ArrayList<>();
        permissionRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public Optional<Permission> findByName(String name) {
        return permissionRepository.findByName(name);
    }

    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }
}
