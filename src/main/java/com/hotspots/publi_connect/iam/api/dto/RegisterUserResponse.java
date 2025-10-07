package com.hotspots.publi_connect.iam.api.dto;

import com.hotspots.publi_connect.iam.vo.ResponseCookiesVo;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserResponse (
    @NotBlank
    ResponseCookiesVo responseCookies
) {}
