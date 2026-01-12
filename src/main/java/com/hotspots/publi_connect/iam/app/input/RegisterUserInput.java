package com.hotspots.publi_connect.iam.app.input;

import com.hotspots.publi_connect.iam.vo.MacAddressVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserInput(

	@NotNull @Valid
	CreateAccountInput createAccountInput,
	
	@NotNull @Valid
	MacAddressVo macAddress,

	@NotBlank
	String name,

	@NotNull
	Integer age,

	@NotBlank
	String gender,

	@NotBlank
	String zipCode,

	@NotBlank
	String authProvider,
	
	@NotBlank
	String pwd
) {}