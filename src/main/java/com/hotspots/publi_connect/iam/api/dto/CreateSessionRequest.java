package com.hotspots.publi_connect.iam.api.dto;

import com.hotspots.publi_connect.iam.vo.DeviceUserIdsVo;

import jakarta.validation.constraints.NotBlank;

public record CreateSessionRequest(
    @NotBlank
    DeviceUserIdsVo deviceUserIdsVo
) {}
