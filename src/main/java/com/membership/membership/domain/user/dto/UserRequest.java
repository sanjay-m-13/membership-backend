package com.membership.membership.domain.user.dto;

import com.membership.membership.domain.tenant.Tenant;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private String role;
    private String status;
    private Long tenantId;
}
