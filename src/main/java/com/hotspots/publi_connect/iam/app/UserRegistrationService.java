package com.hotspots.publi_connect.iam.app;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.app.input.CreateDeviceInput;
import com.hotspots.publi_connect.iam.app.input.CreateUserInput;
import com.hotspots.publi_connect.iam.app.input.RegisterUserInput;
import com.hotspots.publi_connect.iam.app.output.CreateSessionResult;
import com.hotspots.publi_connect.iam.domain.service.AccountService;
import com.hotspots.publi_connect.iam.domain.service.AuthService;
import com.hotspots.publi_connect.iam.domain.service.CredentialService;
import com.hotspots.publi_connect.iam.domain.service.DeviceService;
import com.hotspots.publi_connect.iam.domain.service.UserService;
import com.hotspots.publi_connect.iam.vo.UUIDVo;
import com.hotspots.publi_connect.kernel.utils.mappers.CreateAccountInputMapper;
import com.hotspots.publi_connect.kernel.utils.mappers.CreateUserInputMapper;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class UserRegistrationService {
    private final AccountService accountService;
    private final UserService userService;
    private final CredentialService credentialService;
    private final DeviceService deviceService;
    private final AuthService authService;

    private final CreateUserInputMapper createUserInputMapper;
    private final CreateAccountInputMapper createAccountInputMapper;

    public Mono<CreateSessionResult> registerUser(@Valid RegisterUserInput request) {
        return accountService.createAccount(createAccountInputMapper.toCreateAccountInput(request))
                .flatMap(accountIdSaved -> {
                    CreateUserInput req = createUserInputMapper.toValidatedInput(request, accountIdSaved);
                    return userService.createUser(req);
                })
                    .flatMap(accountIdSaved ->
                             credentialService.createCredential(new UUIDVo(accountIdSaved.toString()), request.pwd())
                        )
                            .flatMap(credentialSaved -> {
                                CreateDeviceInput deviceRequest = new CreateDeviceInput(new UUIDVo(credentialSaved.accountId().toString()), request.macAddressVo());
                                return deviceService.createDevice(deviceRequest);
                            })
                                .flatMap(authService::createSession);
    }

}