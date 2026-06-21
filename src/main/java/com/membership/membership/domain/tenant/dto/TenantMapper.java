package com.membership.membership.domain.tenant.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.membership.membership.domain.tenant.Tenant;
import com.membership.membership.domain.user.User;
import com.membership.membership.domain.user.dto.UserResponse;

public class TenantMapper {

    public static TenantResponse toResponse(Tenant tenant) {
        if (tenant == null) {
            return null;
        }
        
        List<UserResponse> users = tenant.getUsers() != null
                ? tenant.getUsers().stream()
                    .map(TenantMapper::toUserResponse)
                    .collect(Collectors.toList())
                : null;

        return TenantResponse.builder()
                .id(tenant.getId())
                .name(tenant.getName())
                .subDomain(tenant.getSubDomain())
                .status(tenant.getStatus())
                .createdAt(tenant.getCreatedAt())
                .users(users)
                .build();
    }

    public static TenantBasicResponse toBasicResponse(Tenant tenant) {
        if (tenant == null) {
            return null;
        }

        return TenantBasicResponse.builder()
                .id(tenant.getId())
                .name(tenant.getName())
                .subDomain(tenant.getSubDomain())
                .status(tenant.getStatus())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .tenant(toBasicResponse(user.getTenant()))
                .build();
    }
}
