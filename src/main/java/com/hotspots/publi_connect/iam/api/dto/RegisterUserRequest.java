package com.hotspots.publi_connect.iam.api.dto;

import com.hotspots.publi_connect.iam.vo.MacAddressVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequest(
    @NotNull
    @Valid
    CreateUserRequest CreateUserRequest,
    
    @NotNull
    @Valid
    MacAddressVo macAddress,

    @NotBlank
    String pwd
) {}
