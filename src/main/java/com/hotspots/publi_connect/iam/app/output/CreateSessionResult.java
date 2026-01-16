package com.hotspots.publi_connect.iam.app.output;

import org.springframework.http.ResponseCookie;
import org.springframework.lang.NonNull;


public record CreateSessionResult(
    @NonNull
    ResponseCookie sessionCookie,
    
    @NonNull
    ResponseCookie refreshCookie
){}
