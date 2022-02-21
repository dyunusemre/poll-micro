package com.yundi.polluserservice.consumer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserData {
    private String name;
    private String lastName;
    private String username;
    private String email;
}
