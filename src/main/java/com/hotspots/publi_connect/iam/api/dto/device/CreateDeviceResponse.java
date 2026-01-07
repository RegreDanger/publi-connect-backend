package com.hotspots.publi_connect.iam.api.dto.device;

import com.hotspots.publi_connect.iam.vo.DeviceUserIdsVo;

import jakarta.validation.constraints.NotNull;

public record CreateDeviceResponse(
    @NotNull
    DeviceUserIdsVo deviceUserIdsVo
) {}
