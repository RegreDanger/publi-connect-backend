package com.hotspots.publi_connect.iam.api.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateUserResponse(
    @NotNull
	@org.hibernate.validator.constraints.UUID
	UUID userUuid
) {}
