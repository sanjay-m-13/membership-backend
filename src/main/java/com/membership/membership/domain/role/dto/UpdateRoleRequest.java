package com.membership.membership.domain.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UpdateRoleRequest {

    private String role;
    private String description;
}
