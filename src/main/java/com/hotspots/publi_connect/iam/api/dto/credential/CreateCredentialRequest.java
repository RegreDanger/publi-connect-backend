package com.hotspots.publi_connect.iam.api.dto.credential;

import com.hotspots.publi_connect.iam.vo.UUIDVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCredentialRequest(
    @NotNull
    @Valid
    UUIDVo userId,

    @NotBlank
    String pwd

) {}
