package com.hotspots.publi_connect.iam.api.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginAccountRequest(

    @NotBlank
    String email,

    @NotBlank
    String pwd

) {}
