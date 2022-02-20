package com.yundi.pollauthservice.userauth.model;

import com.yundi.pollauthservice.userauth.enums.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document("Role")
public class Role {
    @Id
    private String id;
    private RoleEnum name;
}
