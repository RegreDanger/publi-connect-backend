package com.hotspots.publi_connect.kernel.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.hotspots.publi_connect.iam.api.dto.auth.RegisterUserRequest;
import com.hotspots.publi_connect.iam.app.input.CreateAccountInput;
import com.hotspots.publi_connect.iam.app.input.RegisterUserInput;
import com.hotspots.publi_connect.iam.vo.AccountTypeVo;
import com.hotspots.publi_connect.iam.vo.EmailVo;
import com.hotspots.publi_connect.iam.vo.PhoneNoVo;
import com.hotspots.publi_connect.kernel.utils.enums.AccountType;
import com.hotspots.publi_connect.kernel.utils.enums.AuthProvider;

@Mapper(componentModel = "spring", imports = { AuthProvider.class })
public interface RegisterUserRequestMapper {

	@Mapping(target = "createAccountInput", source = ".", qualifiedByName = "toCreateAccountInput")
	@Mapping(target = "macAddress", expression = "java(new MacAddressVo(plainRequest.macAddress()))")
	
	@Mapping(target = "name", source = "name")
	@Mapping(target = "age", source = "age")
	@Mapping(target = "gender", source = "gender")
	@Mapping(target = "zipCode", source = "zipCode")
	@Mapping(target = "authProvider", expression = "java(AuthProvider.BASIC.toString())")
	@Mapping(target = "pwd", source = "pwd")
	RegisterUserInput toValidatedRequest(RegisterUserRequest plainRequest);

	@Named("toCreateAccountInput")
	default CreateAccountInput toCreateAccountInput(RegisterUserRequest plainRequest) {
		return new CreateAccountInput(
			new EmailVo(plainRequest.email()),
			new PhoneNoVo(plainRequest.phoneNo()),
			new AccountTypeVo(AccountType.USER.toString()));
	}
}