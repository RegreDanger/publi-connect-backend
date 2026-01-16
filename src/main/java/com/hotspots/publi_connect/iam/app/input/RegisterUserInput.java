package com.hotspots.publi_connect.iam.app.input;

import com.hotspots.publi_connect.iam.vo.AccountTypeVo;
import com.hotspots.publi_connect.iam.vo.AgeVo;
import com.hotspots.publi_connect.iam.vo.AuthProviderVo;
import com.hotspots.publi_connect.iam.vo.EmailVo;
import com.hotspots.publi_connect.iam.vo.GenderVo;
import com.hotspots.publi_connect.iam.vo.MacAddressVo;
import com.hotspots.publi_connect.iam.vo.PhoneNoVo;
import com.hotspots.publi_connect.iam.vo.ZipCodeVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserInput(

	@NotBlank
	String name,

	@NotNull
	@Valid
	AgeVo ageVo,

	@NotNull
	@Valid
	GenderVo genderVo,

	@NotNull
	@Valid
	ZipCodeVo zipCodeVo,

	@NotNull
	@Valid
	AuthProviderVo authProviderVo,
	
	@NotBlank
	String pwd,

	//These parameters are for the CreateAccountInput class

	@NotNull
	@Valid
	MacAddressVo macAddressVo,

	@NotNull
	@Valid
	EmailVo emailVo,
	
	@NotNull
	@Valid
	PhoneNoVo phoneNoVo,

	@NotNull
	@Valid
	AccountTypeVo accountTypeVo

) {}