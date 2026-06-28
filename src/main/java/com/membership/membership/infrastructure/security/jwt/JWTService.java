package com.membership.membership.infrastructure.security.jwt;

import com.membership.membership.domain.user.User;
import com.membership.membership.domain.user.UserRepo;
import com.membership.membership.infrastructure.exception.ResourceNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Autowired
    private UserRepo userRepo;


    private String secretKey = "VGhpc0lzQVN1cGVyU2VjcmV0S2V5Rm9ySldUU2lnbmluZ1RoYXRNdXN0QmVBdExlYXN0MzJCeXRlc0xvbmc=";

    private SecretKey signInKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        signInKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String userName) {

        User user = userRepo.findByEmail(userName)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with : " + userName));

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
         claims.put("role", user.getRole());

        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()
                        + 1000L * 60 * 60 * 24))
                .signWith(signInKey)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signInKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token,
                               Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token,
                                 UserDetails userDetails) {

        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}