package com.hotspots.publi_connect.iam.domain.service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.app.output.CreateSessionResult;
import com.hotspots.publi_connect.iam.domain.entity.Session;
import com.hotspots.publi_connect.iam.repository.SessionRepository;
import com.hotspots.publi_connect.iam.vo.DeviceUserIdsVo;
import com.hotspots.publi_connect.iam.vo.RefreshTokenVo;
import com.hotspots.publi_connect.iam.vo.SessionStampsVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;
import com.hotspots.publi_connect.platform.spring.config.JwtConfig;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class SessionService {
	private final SessionRepository repo;
	private final TokenService jwt;
	private final JwtConfig jwtConfig;

	public Mono<CreateSessionResult> createSession(@Valid DeviceUserIdsVo deviceUserIdsVo) {
		UUIDVo userId = deviceUserIdsVo.userId();
		UUIDVo deviceId = deviceUserIdsVo.deviceId();
		
		String refreshToken = jwt.generateRefreshToken(deviceId, userId);
		String sessionToken = jwt.generateSessionToken(deviceId, userId);
		
		SessionStampsVo stamps = new SessionStampsVo(LocalDateTime.now(),LocalDateTime.now().plusMonths(1));
		
		RefreshTokenVo refreshVo = new RefreshTokenVo(refreshToken);
		
		Session session = new Session(deviceId, refreshVo, stamps);
		return repo.save(session).map(sessionSaved -> buildCookies(sessionToken, refreshToken, jwtConfig.getExpiration().getTime(), jwtConfig.getRefreshTime()));
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
