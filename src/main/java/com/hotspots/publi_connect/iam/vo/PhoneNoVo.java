package com.hotspots.publi_connect.iam.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PhoneNoVo(
    @NotBlank
    @Size(min = 10, max = 10, message = "Number should be valid")
	String phoneNo
) {}
