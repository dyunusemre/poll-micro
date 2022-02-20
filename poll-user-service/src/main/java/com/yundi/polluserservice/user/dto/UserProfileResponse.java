package com.yundi.polluserservice.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private String name;
    private String lastName;
    private String email;
    private String username;
}
