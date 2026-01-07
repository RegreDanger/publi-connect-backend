package com.hotspots.publi_connect.iam.domain.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.hotspots.publi_connect.iam.vo.UUIDVo;
import com.hotspots.publi_connect.platform.spring.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenService {

    private final JwtConfig jwtProperties;

    public TokenService(JwtConfig jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateSessionToken(UUIDVo deviceId, UUIDVo userId) {
        return generateToken(UUID.fromString(deviceId.id()), UUID.fromString(userId.id()), jwtProperties.getExpiration().getTime());
    }

    public String generateRefreshToken(UUIDVo deviceId, UUIDVo userId) {
        return generateToken(UUID.fromString(deviceId.id()), UUID.fromString(userId.id()), jwtProperties.getRefreshTime());
    }

    private String generateToken(UUID deviceId, UUID userId, long expirationMs) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("device_id", deviceId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecret().getKey().getBytes()))
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                                     .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecret().getKey().getBytes()))
                                     .build()
                                     .parseClaimsJws(token);

            Date expiration = claims.getBody().getExpiration();
            return expiration.after(new Date());

        } catch (JwtException e) {
            return false;
        } 
    }

}
