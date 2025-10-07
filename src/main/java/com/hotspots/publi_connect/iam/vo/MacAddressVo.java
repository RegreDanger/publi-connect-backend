package com.hotspots.publi_connect.iam.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MacAddressVo(
    @NotBlank
	@Pattern(regexp = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")
	String macAddress
) {}
