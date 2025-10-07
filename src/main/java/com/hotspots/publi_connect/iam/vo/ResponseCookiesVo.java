package com.hotspots.publi_connect.iam.vo;

import org.springframework.http.ResponseCookie;

import jakarta.validation.constraints.NotNull;

public record ResponseCookiesVo(
    @NotNull
    ResponseCookie sessionCookie,
    
    @NotNull
    ResponseCookie refreshCookie
){}
