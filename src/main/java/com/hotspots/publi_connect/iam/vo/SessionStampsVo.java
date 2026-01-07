package com.hotspots.publi_connect.iam.vo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record SessionStampsVo(

    @NotNull
    @PastOrPresent
    LocalDateTime createdAt,

    @NotNull
    @Future
    LocalDateTime expiresAt
) {}
