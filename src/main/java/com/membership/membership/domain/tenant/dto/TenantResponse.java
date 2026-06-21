package com.membership.membership.domain.tenant.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.membership.membership.domain.user.dto.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse {

    private Long id;
    private String name;
    private String subDomain;
    private String status;
    private LocalDateTime createdAt;
    private List<UserResponse> users;

}
