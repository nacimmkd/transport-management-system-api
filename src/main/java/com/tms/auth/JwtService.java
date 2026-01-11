package com.tms.auth;

import com.tms.employees.EmployeeRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String TOKEN_SECRET_KEY;
    final long TOKEN_EXPIRATION = 86400; // 1d


    public String generateToken(UUID userId, UUID companyId, EmployeeRole role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId.toString());
        claims.put("companyId", companyId.toString());
        claims.put("role", role);
        return Jwts.builder()
                .subject(userId.toString())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(extractClaims(token).get("userId", String.class));
    }

    public UUID extractCompanyId(String token) {
        return UUID.fromString(extractClaims(token).get("companyId", String.class));
    }

    public EmployeeRole extractUserRole(String token) {
        return extractClaims(token).get("role", EmployeeRole.class);
    }


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(TOKEN_SECRET_KEY.getBytes());
    }
}
