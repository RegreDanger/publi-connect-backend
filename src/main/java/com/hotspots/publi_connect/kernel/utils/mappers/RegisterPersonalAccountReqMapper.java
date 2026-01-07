package com.hotspots.publi_connect.kernel.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.hotspots.publi_connect.iam.api.dto.account.CreateAccountReq;
import com.hotspots.publi_connect.iam.api.dto.auth.RegisterPersonalAccountHttpReq;
import com.hotspots.publi_connect.iam.api.dto.auth.RegisterPersonalAccountReq;
import com.hotspots.publi_connect.iam.vo.AccountTypeVo;
import com.hotspots.publi_connect.iam.vo.EmailVo;
import com.hotspots.publi_connect.iam.vo.PhoneNoVo;
import com.hotspots.publi_connect.kernel.utils.enums.AccountType;

@Mapper(componentModel = "spring")
public interface RegisterPersonalAccountReqMapper {

	@Mapping(target = "createAccountReq", source = ".", qualifiedByName = "toCreateAccountReq")
	@Mapping(target = "macAddress", expression = "java(new MacAddressVo(plainRequest.macAddress()))")
	
	@Mapping(target = "name", source = "name")
	@Mapping(target = "age", source = "age")
	@Mapping(target = "gender", source = "gender")
	@Mapping(target = "zipCode", source = "zipCode")
	@Mapping(target = "authProvider", expression = "java(\"basic\")")
	@Mapping(target = "pwd", source = "pwd")
	RegisterPersonalAccountReq toValidatedRequest(RegisterPersonalAccountHttpReq plainRequest);

	@Named("toCreateAccountReq")
	default CreateAccountReq toCreateAccountReq(RegisterPersonalAccountHttpReq plainRequest) {
		return new CreateAccountReq(
			new EmailVo(plainRequest.email()),
			new PhoneNoVo(plainRequest.phoneNo()),
			new AccountTypeVo(AccountType.PERSONAL.toString()));
	}
}