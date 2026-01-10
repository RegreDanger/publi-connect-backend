package com.hotspots.publi_connect.iam.domain.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.api.dto.account.CreateAccountRes;
import com.hotspots.publi_connect.iam.app.input.CreateAccountInput;
import com.hotspots.publi_connect.iam.domain.entity.Account;
import com.hotspots.publi_connect.iam.repository.AccountRepository;
import com.hotspots.publi_connect.iam.vo.StampsVo;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class AccountService {
    private final AccountRepository repo;

    public Mono<CreateAccountRes> createAccount(@Valid CreateAccountInput request) {
        StampsVo stampsVo = new StampsVo(LocalDateTime.now(), LocalDateTime.now());
        Account account = new Account(request.emailVo(), request.phoneNoVo(), request.accountTypeVo(), stampsVo);
        return repo.save(account).map(accountSaved -> new CreateAccountRes(accountSaved.getAccountId()));
    }
    
    public Mono<Boolean> accountExists() {
        return repo.existsByEmail(null);
    } 

}
