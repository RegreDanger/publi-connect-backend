package com.hotspots.publi_connect.iam.app.input;

import jakarta.validation.constraints.NotBlank;

public record ValidateCurpInput(
    @NotBlank
    String curp
) {}
