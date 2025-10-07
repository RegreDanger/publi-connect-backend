package com.hotspots.publi_connect.iam.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DeviceUserIdsVo(
    
    @NotNull
    @Valid
    UUIDVo deviceId,

    @NotNull
    @Valid
    UUIDVo userId
) {}
