package com.hotspots.publi_connect.iam.app.input;

import com.hotspots.publi_connect.iam.vo.MacAddressVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateDeviceInput(
	
	@NotNull
	@Valid
	UUIDVo userId,
	
	@NotNull
	@Valid
    MacAddressVo macAddress

) {}
