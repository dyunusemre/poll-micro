package com.yundi.pollauthservice.userauth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Getter
@Setter
@Builder
@Document("user-auth")
public class UserAuth {
    @Id
    private String id;
    private String username;

    private String password;

    @DBRef
    private Set<Role> roles;
}
