package com.hotspots.publi_connect.iam.api.dto;

import java.util.UUID;

import com.hotspots.publi_connect.iam.vo.EmailVo;
import com.hotspots.publi_connect.kernel.utils.annotations.ValidEnum;
import com.hotspots.publi_connect.kernel.utils.enums.AuthProvider;
import com.hotspots.publi_connect.kernel.utils.enums.Gender;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
	@NotBlank
	@org.hibernate.validator.constraints.UUID
	UUID uuid,

    @Size(min = 2, max = 50)
	String name,

	@Min(value = 18, message = "Age should not be less than 18")
	@Max(value = 150, message = "Age should not be greater than 150")
	Integer age,

	@ValidEnum(enumClass = Gender.class, message = "Gender must be MALE or FEMALE")
	String gender,

	EmailVo email,

	@Size(min = 10, max = 10, message = "Number should be valid")
	String phoneNo,

	@Pattern(regexp = "^(0[1-9]\\d{3}|[1-9]\\d{4})$", message = "Zipcode should be valid")
	String zipCode,

	@ValidEnum(enumClass = AuthProvider.class, message = "Auth provider must be Basic, Google, Apple or Facebook")
	String authProvider,

	Boolean isActive 
) {}
