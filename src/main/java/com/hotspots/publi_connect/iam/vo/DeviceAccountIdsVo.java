package com.hotspots.publi_connect.iam.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DeviceAccountIdsVo(
    
    @NotNull
    @Valid
    UUIDVo deviceIdVo,

    @NotNull
    @Valid
    UUIDVo accountIdVo
) {}
