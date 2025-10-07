package com.hotspots.publi_connect.iam.api.dto;

import com.hotspots.publi_connect.iam.vo.CredentialStampsVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateCredentialResponse(
    @NotNull
    @Valid
    UUIDVo userId,

    @NotNull
    @Valid
    CredentialStampsVo stamps

) {}
