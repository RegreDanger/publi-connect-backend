package com.hotspots.publi_connect.iam.vo;

import com.hotspots.publi_connect.kernel.utils.annotations.ValidEnum;
import com.hotspots.publi_connect.kernel.utils.enums.AuthProvider;

import jakarta.validation.constraints.NotBlank;

public record AuthProviderVo(
    @NotBlank
    @ValidEnum(enumClass = AuthProvider.class, message = "authProvider must be Basic, Google, Apple or Facebook")
	String authProvider
) {}
