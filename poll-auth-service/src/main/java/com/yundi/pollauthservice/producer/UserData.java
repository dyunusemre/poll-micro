package com.yundi.pollauthservice.producer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserData {
    private String name;
    private String lastName;
    private String email;
    private String username;
}
