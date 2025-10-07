package com.hotspots.publi_connect.iam.domain.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.api.dto.CreateCredentialRequest;
import com.hotspots.publi_connect.iam.api.dto.CreateCredentialResponse;
import com.hotspots.publi_connect.iam.domain.entity.Credential;
import com.hotspots.publi_connect.iam.repository.CredentialRepository;
import com.hotspots.publi_connect.iam.vo.CredentialStampsVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Validated
@Data
public class CredentialService {
	private final CredentialRepository repo;
	private final PasswordEncoder encoder;

	@Valid
	public Mono<CreateCredentialResponse> createCredential(CreateCredentialRequest request) {
		return Mono.fromCallable(() -> {
					String hashed = encoder.encode(request.pwd());
					LocalDateTime createdAt = LocalDateTime.now();
					LocalDateTime updatedAt = LocalDateTime.now();
					CredentialStampsVo stamps = new CredentialStampsVo(createdAt, updatedAt);
					return new Credential(request.userId(), hashed, stamps);
				})
				.subscribeOn(Schedulers.boundedElastic())
					.flatMap(repo::save)
						.map(saved -> {
							CredentialStampsVo stamps = new CredentialStampsVo(saved.getCreatedAt(), saved.getUpdatedAt());
							UUIDVo userId = new UUIDVo(saved.getUserId());
							return new CreateCredentialResponse(userId, stamps);
						});
	}
}
