package com.yundi.pollauthservice.auth.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String username;

    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;
}
