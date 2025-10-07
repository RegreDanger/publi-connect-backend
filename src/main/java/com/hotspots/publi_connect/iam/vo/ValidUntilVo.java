package com.hotspots.publi_connect.iam.vo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record ValidUntilVo(
    @NotNull
    @Future
    LocalDateTime validUntil
) {}
