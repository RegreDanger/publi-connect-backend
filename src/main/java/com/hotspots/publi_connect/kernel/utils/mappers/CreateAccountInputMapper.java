package com.hotspots.publi_connect.kernel.utils.mappers;

import org.mapstruct.Mapper;

import com.hotspots.publi_connect.iam.app.input.CreateAccountInput;
import com.hotspots.publi_connect.iam.app.input.RegisterUserInput;

@Mapper(componentModel = "spring")
public interface CreateAccountInputMapper {
	
	CreateAccountInput toCreateAccountInput(RegisterUserInput input);
    
}
