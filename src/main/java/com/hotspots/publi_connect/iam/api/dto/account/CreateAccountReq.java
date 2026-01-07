package com.hotspots.publi_connect.iam.api.dto.account;

import com.hotspots.publi_connect.iam.vo.AccountTypeVo;
import com.hotspots.publi_connect.iam.vo.EmailVo;
import com.hotspots.publi_connect.iam.vo.PhoneNoVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateAccountReq (

	@NotNull @Valid
	EmailVo emailVo,
	
	@NotNull @Valid
	PhoneNoVo phoneNoVo,
	
	@NotNull @Valid
	AccountTypeVo accountTypeVo

){}
