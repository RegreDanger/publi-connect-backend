package com.hotspots.publi_connect.iam.vo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record StampsVo(
    @NotNull
	@PastOrPresent
	LocalDateTime createdAt,

	@NotNull
	@PastOrPresent
	LocalDateTime updatedAt

) {}