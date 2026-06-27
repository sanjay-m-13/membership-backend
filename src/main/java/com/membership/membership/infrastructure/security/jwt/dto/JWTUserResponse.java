package com.membership.membership.infrastructure.security.jwt.dto;

import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.JwtBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTUserResponse {

    private Long userId;
    private String username;
    private String token;
    private JwtBuilder claims;
}
