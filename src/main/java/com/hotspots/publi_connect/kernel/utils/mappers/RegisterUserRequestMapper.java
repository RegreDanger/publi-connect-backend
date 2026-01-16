package com.hotspots.publi_connect.kernel.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotspots.publi_connect.iam.api.dto.auth.RegisterUserRequest;
import com.hotspots.publi_connect.iam.app.input.RegisterUserInput;
import com.hotspots.publi_connect.kernel.utils.enums.AccountType;
import com.hotspots.publi_connect.kernel.utils.enums.AuthProvider;

@Mapper(componentModel = "spring", imports = { AuthProvider.class, AccountType.class })
public interface RegisterUserRequestMapper {

	@Mapping(target = "name", source = "name")
	@Mapping(target = "ageVo", expression = "java(new AgeVo(plainRequest.age()))")
	@Mapping(target = "genderVo", expression = "java(new GenderVo(plainRequest.gender()))")
	@Mapping(target = "zipCodeVo", expression = "java(new ZipCodeVo(plainRequest.zipCode()))")
	@Mapping(target = "authProviderVo", expression = "java(new AuthProviderVo(AuthProvider.BASIC.toString()))")
	@Mapping(target = "pwd", source = "pwd")
	@Mapping(target = "macAddressVo", expression = "java(new MacAddressVo(plainRequest.macAddress()))")
	@Mapping(target = "emailVo", expression = "java(new EmailVo(plainRequest.email()))")
	@Mapping(target = "phoneNoVo", expression = "java(new PhoneNoVo(plainRequest.phoneNo()))")
	@Mapping(target = "accountTypeVo", expression = "java(new AccountTypeVo(AccountType.USER.toString()))")
	RegisterUserInput toValidatedRequest(RegisterUserRequest plainRequest);
}