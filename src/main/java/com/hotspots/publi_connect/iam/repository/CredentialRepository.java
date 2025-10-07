package com.hotspots.publi_connect.iam.repository;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.hotspots.publi_connect.iam.domain.entity.Credential;

import reactor.core.publisher.Mono;

@Repository
public interface CredentialRepository extends R2dbcRepository<Credential, UUID> {
    public Mono<Credential> findByUserId(UUID userId);
}
