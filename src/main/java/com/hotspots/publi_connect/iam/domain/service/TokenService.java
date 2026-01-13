package com.hotspots.publi_connect.iam.domain.service;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.hotspots.publi_connect.iam.vo.UUIDVo;
import com.hotspots.publi_connect.platform.spring.config.JwtConfig;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenService {

    private final JwtConfig jwtProperties;
    private final SecretKey key;
    private final JwtParser parser;

    public TokenService(JwtConfig jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getKey().getBytes());
        this.parser = Jwts.parser().verifyWith(this.key).build();
    }

    public String generateSessionToken(UUIDVo accountIdVo) {
        return generateToken(UUID.fromString(accountIdVo.id()), jwtProperties.getExpiration().getTime());
    }

    public String generateRefreshToken(UUIDVo accountId) {
        return generateToken(UUID.fromString(accountId.id()), jwtProperties.getRefreshTime());
    }

    private String generateToken(UUID accountId, long expirationMs) {
        return Jwts.builder()
                .subject(accountId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecret().getKey().getBytes()))
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Date expiration = parser.parseSignedClaims(token)
                                        .getPayload()
                                        .getExpiration();
            return expiration.after(new Date());

        } catch (JwtException e) {
            return false;
        }
    }

    public String extractSubject(String token) {
        return parser.parseSignedClaims(token).getPayload().getSubject();
    }

}
