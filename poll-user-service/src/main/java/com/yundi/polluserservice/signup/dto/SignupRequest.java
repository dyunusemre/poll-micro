package com.yundi.polluserservice.signup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
public class SignupRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;

    private String password;
}
