package com.membership.membership.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserStatusRequest {

    @NotBlank(message = "Status is required!")
    private String status;
}
