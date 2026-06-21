package com.membership.membership.domain.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TenantRequest {

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Subdomain is required!")
    private String subDomain;

    @NotBlank(message = "Status is required!")
    private String status;
}
