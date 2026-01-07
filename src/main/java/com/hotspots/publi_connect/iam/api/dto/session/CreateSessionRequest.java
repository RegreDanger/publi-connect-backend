package com.hotspots.publi_connect.iam.api.dto.session;

import com.hotspots.publi_connect.iam.vo.DeviceUserIdsVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateSessionRequest(
    @NotNull
    @Valid
    DeviceUserIdsVo deviceUserIdsVo
) {}
