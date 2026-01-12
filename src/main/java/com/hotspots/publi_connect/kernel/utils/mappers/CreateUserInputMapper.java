package com.hotspots.publi_connect.kernel.utils.mappers;

import java.util.UUID;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotspots.publi_connect.iam.app.input.CreateUserInput;
import com.hotspots.publi_connect.iam.app.input.RegisterUserInput;

@Mapper(componentModel = "spring")
public interface CreateUserInputMapper {
    
    @Mapping(target = "accountIdVo", expression = "java(new UUIDVo(accountId.toString()))")
    @Mapping(target = "name", expression = "java(input.name())")
    @Mapping(target = "ageVo", expression = "java(new AgeVo(input.age()))")
    @Mapping(target = "genderVo", expression = "java(new GenderVo(input.gender()))")
    @Mapping(target = "zipCodeVo", expression = "java(new ZipCodeVo(input.zipCode()))")
    @Mapping(target = "authProviderVo", expression = "java(new AuthProviderVo(input.authProvider()))")
    @Mapping(target = "isActiveVo", expression = "java(true)")
    CreateUserInput toValidatedInput(@Context RegisterUserInput input, UUID accountId);
}
