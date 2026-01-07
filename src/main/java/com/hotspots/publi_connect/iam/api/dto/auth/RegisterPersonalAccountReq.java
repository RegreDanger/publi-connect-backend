package com.hotspots.publi_connect.iam.api.dto.auth;

import com.hotspots.publi_connect.iam.api.dto.account.CreateAccountReq;
import com.hotspots.publi_connect.iam.vo.MacAddressVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterPersonalAccountReq(

	@NotNull @Valid
	CreateAccountReq createAccountReq,
	
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