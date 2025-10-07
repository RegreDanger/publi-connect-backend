package com.hotspots.publi_connect.iam.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ZipCodeVo(
    @NotBlank
    @Pattern(regexp = "^(0[1-9]\\d{3}|[1-9]\\d{4})$", message = "Zipcode should be valid")
	String zipCode
) {}
