package com.membership.membership.domain.user.dto;

import lombok.Data;

@Data
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private String role;
    private String status;
    private Long tenantId;
}
