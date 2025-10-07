package com.hotspots.publi_connect.iam.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.api.dto.CreateUserRequest;
import com.hotspots.publi_connect.iam.api.dto.CreateUserResponse;
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

	@Valid
	public Mono<CreateUserResponse> createUser(CreateUserRequest request) {
		User user = new User(request.name(), request.age(), request.gender(), request.email(), request.phoneNo(), request.zipCode(), request.authProvider(), request.isActive());
		return repo.save(user).map(userSaved -> new CreateUserResponse(userSaved.getUserId()));
	}

}
