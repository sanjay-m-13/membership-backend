package com.membership.membership.domain.user.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
