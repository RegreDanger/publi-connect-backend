package com.hotspots.publi_connect.kernel.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotspots.publi_connect.iam.api.dto.auth.LoginAccountRequest;
import com.hotspots.publi_connect.iam.app.input.LoginAccountInput;

@Mapper(componentModel = "spring")
public interface LoginAccountInputMapper {
    
    @Mapping(target = "emailVo", expression = "java(new EmailVo(request.email()))")
    @Mapping(target = "pwd", source = "pwd")    
    LoginAccountInput toInput(LoginAccountRequest request);

}
