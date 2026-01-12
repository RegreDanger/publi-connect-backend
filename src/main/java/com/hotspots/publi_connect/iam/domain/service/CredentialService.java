package com.hotspots.publi_connect.iam.domain.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.app.output.CreateCredentialResult;
import com.hotspots.publi_connect.iam.domain.entity.Credential;
import com.hotspots.publi_connect.iam.repository.CredentialRepository;
import com.hotspots.publi_connect.iam.vo.StampsVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Validated
@Data
public class CredentialService {
	private final CredentialRepository repo;
	private final PasswordEncoder encoder;

	public Mono<CreateCredentialResult> createCredential(@NotNull @Valid UUIDVo accountIdVo, @NotBlank String pwd) {
		return Mono.fromCallable(() -> {
					String hashed = encoder.encode(pwd);
					LocalDateTime createdAt = LocalDateTime.now();
					LocalDateTime updatedAt = LocalDateTime.now();
					StampsVo stamps = new StampsVo(createdAt, updatedAt);
					return new Credential(accountIdVo, hashed, stamps);
				})
				.subscribeOn(Schedulers.boundedElastic())
					.flatMap(repo::save)
						.map(saved -> new CreateCredentialResult(saved.getAccountId(), saved.getCreatedAt(), saved.getUpdatedAt()));
	}
}
