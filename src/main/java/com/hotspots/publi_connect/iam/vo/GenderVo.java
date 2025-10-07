package com.hotspots.publi_connect.iam.vo;

import com.hotspots.publi_connect.kernel.utils.annotations.ValidEnum;
import com.hotspots.publi_connect.kernel.utils.enums.Gender;

import jakarta.validation.constraints.NotBlank;

public record GenderVo(
    @NotBlank
	@ValidEnum(enumClass = Gender.class, message = "Gender must be MALE or FEMALE")
	String gender
) {}
