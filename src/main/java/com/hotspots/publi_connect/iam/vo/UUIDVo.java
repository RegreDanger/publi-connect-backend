package com.hotspots.publi_connect.iam.vo;

import jakarta.validation.constraints.NotNull;

public record UUIDVo(
    @NotNull
    @org.hibernate.validator.constraints.UUID(message = "ID should be valid")
    String id
) {}
