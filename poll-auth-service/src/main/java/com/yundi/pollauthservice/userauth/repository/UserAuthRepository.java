package com.yundi.pollauthservice.userauth.repository;

import com.yundi.pollauthservice.userauth.model.UserAuth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends MongoRepository<UserAuth, String> {
    Optional<UserAuth> findByUsername(String username);
}
