package com.hotspots.publi_connect.iam.api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterPersonalAccountHttpReq(
    
    @NotBlank
    String name,

    @NotNull
    Integer age,

    @NotBlank
    String gender,
    
    @NotBlank
    String email,
    
    @NotBlank
    String phoneNo,
    
    @NotBlank
    String zipCode,
    
    @NotBlank
    String macAddress,
    
    @NotBlank
    String pwd
) {}