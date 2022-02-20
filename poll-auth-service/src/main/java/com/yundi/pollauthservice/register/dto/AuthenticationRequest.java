package com.yundi.pollauthservice.register.dto;

import com.yundi.pollauthservice.register.validator.ValidPassword;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank
    private String username;

    private String password;
}
