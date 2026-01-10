package com.hotspots.publi_connect.kernel.utils.mappers;

import java.util.UUID;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotspots.publi_connect.iam.app.input.CreatePersonalAccountInput;
import com.hotspots.publi_connect.iam.app.input.RegisterPersonalAccountInput;

@Mapper(componentModel = "spring")
public interface CreatePersonalAccountReqMapper {
    
    @Mapping(target = "uuidVo", expression = "java(new UUIDVo(accountId.toString()))")
    @Mapping(target = "nameVo", expression = "java(request.name())")
    @Mapping(target = "ageVo", expression = "java(new AgeVo(request.age()))")
    @Mapping(target = "genderVo", expression = "java(new GenderVo(request.gender()))")
    @Mapping(target = "zipCodeVo", expression = "java(new ZipCodeVo(request.zipCode()))")
    @Mapping(target = "authProviderVo", expression = "java(new AuthProviderVo(request.authProvider()))")
    @Mapping(target = "isActiveVo", expression = "java(true)")
    CreatePersonalAccountInput toValidatedRequest(@Context RegisterPersonalAccountInput request, UUID accountId);
}
