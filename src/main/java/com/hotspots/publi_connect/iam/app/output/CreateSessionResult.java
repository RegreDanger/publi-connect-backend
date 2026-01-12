package com.hotspots.publi_connect.iam.app.output;

import org.springframework.http.ResponseCookie;

public record CreateSessionResult(
    ResponseCookie sessionCookie,
    ResponseCookie refreshCookie
){}
