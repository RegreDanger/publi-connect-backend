package com.hotspots.publi_connect.iam.app;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.api.dto.CreateCredentialRequest;
import com.hotspots.publi_connect.iam.api.dto.CreateDeviceRequest;
import com.hotspots.publi_connect.iam.api.dto.CreateSessionRequest;
import com.hotspots.publi_connect.iam.api.dto.RegisterUserRequest;
import com.hotspots.publi_connect.iam.api.dto.RegisterUserResponse;
import com.hotspots.publi_connect.iam.domain.service.CredentialService;
import com.hotspots.publi_connect.iam.domain.service.DeviceService;
import com.hotspots.publi_connect.iam.domain.service.SessionService;
import com.hotspots.publi_connect.iam.domain.service.UserService;
import com.hotspots.publi_connect.iam.vo.UUIDVo;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class UserAuthService {
    private final UserService userService;
    private final CredentialService credentialService;
    private final DeviceService deviceService;
    private final SessionService sessionService;

    @Valid
    public Mono<RegisterUserResponse> registerUser(RegisterUserRequest request) {
        return userService.createUser(request.CreateUserRequest())
                    .flatMap(userSaved -> {
                        CreateCredentialRequest credentialRequest = new CreateCredentialRequest(new UUIDVo(userSaved.userUuid()), request.pwd());
                        return credentialService.createCredential(credentialRequest);
                    })
                        .flatMap(credentialSaved -> {
                            CreateDeviceRequest deviceRequest = new CreateDeviceRequest(credentialSaved.userId(), request.macAddress());
                            return deviceService.createDevice(deviceRequest);
                        })
                            .flatMap(deviceSaved -> {
                                CreateSessionRequest sessionRequest = new CreateSessionRequest(deviceSaved.deviceUserIdsVo());
                                return sessionService.createSession(sessionRequest);
                            })
                                .map(sessionSaved -> new RegisterUserResponse(sessionSaved.responseCookies()));
    }

}