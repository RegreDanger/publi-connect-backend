package com.hotspots.publi_connect.iam.repository;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.hotspots.publi_connect.iam.domain.entity.Account;

import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends R2dbcRepository<Account, UUID> {
    public Mono<Boolean> existsByEmail(String email);
}
