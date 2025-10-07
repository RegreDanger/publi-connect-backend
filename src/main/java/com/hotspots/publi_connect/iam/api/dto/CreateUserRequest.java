package com.hotspots.publi_connect.iam.api.dto;

import com.hotspots.publi_connect.iam.vo.AgeVo;
import com.hotspots.publi_connect.iam.vo.AuthProviderVo;
import com.hotspots.publi_connect.iam.vo.EmailVo;
import com.hotspots.publi_connect.iam.vo.GenderVo;
import com.hotspots.publi_connect.iam.vo.PhoneNoVo;
import com.hotspots.publi_connect.iam.vo.ZipCodeVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(

	@NotBlank
	String name,
	
	@NotNull
	@Valid
	AgeVo age,

	@NotNull
	@Valid
	GenderVo gender,

	@NotNull
	@Valid
	EmailVo email,

	@NotNull
	@Valid
	PhoneNoVo phoneNo,

	@NotNull
	@Valid
	ZipCodeVo zipCode,

	@NotNull
	@Valid
	AuthProviderVo authProvider,

	@NotNull
	Boolean isActive
	
){}
