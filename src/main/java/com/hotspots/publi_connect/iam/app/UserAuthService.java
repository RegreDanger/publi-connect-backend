package com.hotspots.publi_connect.iam.app;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.api.dto.auth.RegisterPersonalAccountReq;
import com.hotspots.publi_connect.iam.api.dto.auth.RegisterPersonalAccountRes;
import com.hotspots.publi_connect.iam.api.dto.credential.CreateCredentialRequest;
import com.hotspots.publi_connect.iam.api.dto.device.CreateDeviceRequest;
import com.hotspots.publi_connect.iam.api.dto.personal_account.CreatePersonalAccountReq;
import com.hotspots.publi_connect.iam.api.dto.session.CreateSessionRequest;
import com.hotspots.publi_connect.iam.domain.service.AccountService;
import com.hotspots.publi_connect.iam.domain.service.CredentialService;
import com.hotspots.publi_connect.iam.domain.service.DeviceService;
import com.hotspots.publi_connect.iam.domain.service.PersonalAccountService;
import com.hotspots.publi_connect.iam.domain.service.SessionService;
import com.hotspots.publi_connect.iam.vo.UUIDVo;
import com.hotspots.publi_connect.kernel.utils.mappers.CreatePersonalAccountReqMapper;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class UserAuthService {
    private final AccountService accountService;
    private final PersonalAccountService personalAccountService;
    private final CredentialService credentialService;
    private final DeviceService deviceService;
    private final SessionService sessionService;

    private final CreatePersonalAccountReqMapper createPersonalAccountReqMapper;

    public Mono<RegisterPersonalAccountRes> registerPersonalAccount(@Valid RegisterPersonalAccountReq request) {
        return accountService.createAccount(request.createAccountReq())
                .flatMap(accountSaved -> {
                    CreatePersonalAccountReq req = createPersonalAccountReqMapper.toValidatedRequest(request, accountSaved.accountId());
                    return personalAccountService.createPersonalAccount(req);
                })
                    .flatMap(userSaved -> {
                            CreateCredentialRequest credentialRequest = new CreateCredentialRequest(new UUIDVo(userSaved.userUuid().toString()), request.pwd());
                            return credentialService.createCredential(credentialRequest);
                        })
                            .flatMap(credentialSaved -> {
                                CreateDeviceRequest deviceRequest = new CreateDeviceRequest(new UUIDVo(credentialSaved.userId().toString()), request.macAddress());
                                return deviceService.createDevice(deviceRequest);
                            })
                                .flatMap(deviceSaved -> {
                                    CreateSessionRequest sessionRequest = new CreateSessionRequest(deviceSaved.deviceUserIdsVo());
                                    return sessionService.createSession(sessionRequest);
                                })
                                    .map(sessionSaved -> new RegisterPersonalAccountRes(sessionSaved.responseCookies()));
    }

}