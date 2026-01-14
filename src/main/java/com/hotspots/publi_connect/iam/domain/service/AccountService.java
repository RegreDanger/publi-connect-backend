package com.hotspots.publi_connect.iam.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import com.hotspots.publi_connect.iam.app.input.CreateAccountInput;
import com.hotspots.publi_connect.iam.domain.entity.Account;
import com.hotspots.publi_connect.iam.repository.AccountRepository;
import com.hotspots.publi_connect.iam.vo.EmailVo;
import com.hotspots.publi_connect.iam.vo.StampsVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class AccountService {
    private final AccountRepository repo;

    public Mono<UUID> createAccount(@Valid CreateAccountInput request) {
        return accountExists(request.emailVo())
            .filter(bool -> !bool)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado")))
            .flatMap(bool -> {    
                StampsVo stampsVo = new StampsVo(LocalDateTime.now(), LocalDateTime.now());
                Account account = new Account(request.emailVo(), request.phoneNoVo(), request.accountTypeVo(), stampsVo);
                return repo.save(account); 
            })
            .map(Account::getAccountId);
    }
    
    public Mono<Boolean> accountExists(@Valid EmailVo emailVo) {
        return repo.existsByEmail(emailVo.email());
    }

    public Mono<UUIDVo> findAccountByEmail(@Valid EmailVo emailVo) {
        return repo.findByEmail(emailVo.email())
                .map(account -> new UUIDVo(account.getAccountId().toString()));
                
    }

}
