package com.membership.membership.domain.user.dto;

import java.time.LocalDateTime;

import com.membership.membership.domain.tenant.dto.TenantBasicResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String status;
    private LocalDateTime createdAt;
    private TenantBasicResponse tenant;
}
