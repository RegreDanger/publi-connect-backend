package com.hotspots.publi_connect.iam.api.dto.personal_account;

import com.hotspots.publi_connect.iam.vo.AgeVo;
import com.hotspots.publi_connect.iam.vo.AuthProviderVo;
import com.hotspots.publi_connect.iam.vo.GenderVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;
import com.hotspots.publi_connect.iam.vo.ZipCodeVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePersonalAccountReq(

	@NotNull
	@Valid
	UUIDVo uuidVo,

	@NotBlank
	String nameVo,
	
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

	@NotNull
	Boolean isActiveVo
	
){}
