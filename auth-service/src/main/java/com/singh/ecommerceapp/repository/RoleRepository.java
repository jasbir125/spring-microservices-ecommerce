package com.singh.ecommerceapp.repository;


import com.singh.ecommerceapp.entity.Role;
import com.singh.ecommerceapp.entity.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
