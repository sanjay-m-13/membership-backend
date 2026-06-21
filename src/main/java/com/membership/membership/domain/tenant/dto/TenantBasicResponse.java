package com.membership.membership.domain.tenant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantBasicResponse {
    private Long id;
    private String name;
    private String subDomain;
    private String status;
}
