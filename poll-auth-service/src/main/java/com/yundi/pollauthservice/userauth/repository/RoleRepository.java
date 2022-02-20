package com.yundi.pollauthservice.userauth.repository;

import com.yundi.pollauthservice.userauth.enums.RoleEnum;
import com.yundi.pollauthservice.userauth.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(RoleEnum name);
}
