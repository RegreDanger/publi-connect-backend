package com.hotspots.publi_connect.iam.vo;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenVo (
    @NotBlank
    String refreshToken
){}
