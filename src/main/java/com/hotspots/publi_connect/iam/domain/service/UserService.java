package com.hotspots.publi_connect.iam.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.api.dto.personal_account.CreatePersonalAccountRes;
import com.hotspots.publi_connect.iam.app.input.CreateUserInput;
import com.hotspots.publi_connect.iam.domain.entity.User;
import com.hotspots.publi_connect.iam.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class UserService {
	private final UserRepository repo;

	public Mono<CreatePersonalAccountRes> createUser(@Valid CreateUserInput request) {
		User account = new User(request.uuidVo(), request.name(), request.ageVo(), request.genderVo(), request.zipCodeVo(), request.authProviderVo(), request.isActiveVo());
		return repo.save(account).map(userSaved -> new CreatePersonalAccountRes(userSaved.getAccountId()));
	}

}
