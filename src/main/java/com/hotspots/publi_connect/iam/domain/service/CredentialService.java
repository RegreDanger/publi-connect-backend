package com.hotspots.publi_connect.iam.domain.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.api.dto.credential.CreateCredentialRequest;
import com.hotspots.publi_connect.iam.app.output.CreateCredentialResult;
import com.hotspots.publi_connect.iam.domain.entity.Credential;
import com.hotspots.publi_connect.iam.repository.CredentialRepository;
import com.hotspots.publi_connect.iam.vo.StampsVo;

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

	public Mono<CreateCredentialResult> createCredential(@Valid CreateCredentialRequest request) {
		return Mono.fromCallable(() -> {
					String hashed = encoder.encode(request.pwd());
					LocalDateTime createdAt = LocalDateTime.now();
					LocalDateTime updatedAt = LocalDateTime.now();
					StampsVo stamps = new StampsVo(createdAt, updatedAt);
					return new Credential(request.userId(), hashed, stamps);
				})
				.subscribeOn(Schedulers.boundedElastic())
					.flatMap(repo::save)
						.map(saved -> {
							return new CreateCredentialResult(saved.getUserId(), saved.getCreatedAt(), saved.getUpdatedAt());
						});
	}
}
