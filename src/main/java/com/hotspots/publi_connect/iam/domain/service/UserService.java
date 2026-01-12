package com.hotspots.publi_connect.iam.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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

	public Mono<UUID> createUser(@Valid CreateUserInput request) {
		User user = new User(request.uuidVo(), request.name(), request.ageVo(), request.genderVo(), request.zipCodeVo(), request.authProviderVo(), request.isActiveVo());
		return repo.save(user).map(User::getAccountId);
	}

}
