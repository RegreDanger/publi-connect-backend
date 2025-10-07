package com.hotspots.publi_connect.iam.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record EmailVo (
    @NotBlank
    @Email(message = "Email should be valid")
    String email
){}
