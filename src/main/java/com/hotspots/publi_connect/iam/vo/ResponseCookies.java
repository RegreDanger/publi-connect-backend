package com.hotspots.publi_connect.iam.vo;

import org.springframework.http.ResponseCookie;

public record ResponseCookies(
    ResponseCookie sessionCookie,
    ResponseCookie refreshCookie
){}
