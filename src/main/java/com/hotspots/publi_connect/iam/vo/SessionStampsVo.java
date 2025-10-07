package com.hotspots.publi_connect.iam.vo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

public record SessionStampsVo(
    @NotBlank
    @PastOrPresent
    LocalDateTime createdAt,

    @NotBlank
    @Future
    LocalDateTime expiresAt
) {}
