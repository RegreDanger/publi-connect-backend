package com.hotspots.publi_connect.iam.domain.service;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.app.output.CreateSessionResult;
import com.hotspots.publi_connect.iam.vo.DeviceAccountIdsVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;
import com.hotspots.publi_connect.platform.spring.config.JwtConfig;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class AuthService {
	private final TokenService jwt;
	private final JwtConfig jwtConfig;

	public Mono<CreateSessionResult> createSession(@Valid DeviceAccountIdsVo deviceAccountIdsVo) {

		return Mono.just(deviceAccountIdsVo).map(deviceAccountIdsVoMapping -> {
			UUIDVo accountId = deviceAccountIdsVoMapping.accountIdVo();
			UUIDVo deviceId = deviceAccountIdsVoMapping.deviceIdVo();
			
			String refreshToken = jwt.generateRefreshToken(deviceId, accountId);
			String sessionToken = jwt.generateSessionToken(deviceId, accountId);
			return buildCookies(sessionToken, refreshToken, jwtConfig.getExpiration().getTime(), jwtConfig.getRefreshTime());
		});
	}

	private CreateSessionResult buildCookies(String sessionToken, String refreshToken, long expirationTime, long refreshTime) {
		ResponseCookie sessionCookie = ResponseCookie
											.from("session_token", sessionToken)
											.httpOnly(true)
											.secure(true)
											.path("/")
											.maxAge(expirationTime/1000)
											.sameSite("Strict")
											.build();
		ResponseCookie refreshCookie = ResponseCookie
											.from("refresh_token", refreshToken)
											.httpOnly(true)
											.secure(true)
											.path("/")
											.maxAge(refreshTime/1000)
											.sameSite("Strict")
											.build();
		return new CreateSessionResult(sessionCookie, refreshCookie);
	}

}
