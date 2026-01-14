package com.hotspots.publi_connect.iam.app;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import com.hotspots.publi_connect.iam.app.input.LoginAccountInput;
import com.hotspots.publi_connect.iam.app.output.CreateSessionResult;
import com.hotspots.publi_connect.iam.domain.service.AccountService;
import com.hotspots.publi_connect.iam.domain.service.AuthService;
import com.hotspots.publi_connect.iam.domain.service.CredentialService;

import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class LoginAccountUseCase {
    
    private final AccountService accountService;
    private final CredentialService credentialService;
    private final AuthService authService;

    public Mono<CreateSessionResult> loginAccount(@Validated LoginAccountInput input) {
        return accountService.findAccountByEmail(input.emailVo())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales Inválidas")))
                .flatMap(accountFoundIdVo ->
                    credentialService.authenticate(accountFoundIdVo, input.pwd())
                        .thenReturn(accountFoundIdVo)
                )
                .flatMap(authService::createSession);
    }
}
