package com.membership.membership.domain.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TenantStatusRequest {

    @NotBlank(message = "Status is required!")
    private String status;
}
