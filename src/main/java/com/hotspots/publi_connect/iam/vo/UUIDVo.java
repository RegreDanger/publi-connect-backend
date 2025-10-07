package com.hotspots.publi_connect.iam.vo;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record UUIDVo(
    @NotNull
    @org.hibernate.validator.constraints.UUID(message = "ID should be valid")
    UUID id
) {}
