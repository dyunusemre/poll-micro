package com.yundi.pollauthservice.userauth.model;

import com.yundi.pollauthservice.register.validator.ValidPassword;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Builder
@Document("UserAuth")
public class UserAuth {
    @Id
    private String id;
    private String username;

    @ValidPassword
    private String password;
    //private Collection<String> roles;
}
