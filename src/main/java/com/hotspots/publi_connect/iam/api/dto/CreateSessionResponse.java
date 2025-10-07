package com.hotspots.publi_connect.iam.api.dto;

import com.hotspots.publi_connect.iam.vo.ResponseCookiesVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateSessionResponse(
    @NotBlank
    @Valid
    ResponseCookiesVo responseCookies
) {}
