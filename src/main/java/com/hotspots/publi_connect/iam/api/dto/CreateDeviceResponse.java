package com.hotspots.publi_connect.iam.api.dto;

import com.hotspots.publi_connect.iam.vo.DeviceUserIdsVo;

import jakarta.validation.constraints.NotNull;

public record CreateDeviceResponse(
    @NotNull
    DeviceUserIdsVo deviceUserIdsVo
) {}
