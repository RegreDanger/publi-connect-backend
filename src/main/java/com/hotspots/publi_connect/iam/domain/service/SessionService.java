package com.hotspots.publi_connect.iam.domain.service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.api.dto.CreateSessionRequest;
import com.hotspots.publi_connect.iam.api.dto.CreateSessionResponse;
import com.hotspots.publi_connect.iam.domain.entity.Session;
import com.hotspots.publi_connect.iam.repository.SessionRepository;
import com.hotspots.publi_connect.iam.util.JwtUtil;
import com.hotspots.publi_connect.iam.vo.RefreshTokenVo;
import com.hotspots.publi_connect.iam.vo.ResponseCookiesVo;
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
	private final JwtUtil jwt;
	private final JwtConfig jwtConfig;

	@Valid
	public Mono<CreateSessionResponse> createSession(CreateSessionRequest request) {
		UUIDVo userId = request.deviceUserIdsVo().userId();
		UUIDVo deviceId = request.deviceUserIdsVo().deviceId();
		
		String refreshToken = jwt.generateRefreshToken(deviceId, userId);
		String sessionToken = jwt.generateSessionToken(deviceId, userId);
		
		SessionStampsVo stamps = new SessionStampsVo(LocalDateTime.now(),LocalDateTime.now().plusMonths(1));
		
		RefreshTokenVo refreshVo = new RefreshTokenVo(refreshToken);
		
		Session session = new Session(deviceId, refreshVo, stamps);
		return repo.save(session).map(sessionSaved -> {
			ResponseCookiesVo responseCookies = buildCookies(sessionToken, refreshToken, jwtConfig.getExpiration().getTime(), jwtConfig.getRefreshTime());
			return new CreateSessionResponse(responseCookies);
		});
	}

	private ResponseCookiesVo buildCookies(String sessionToken, String refreshToken, long expirationTime, long refreshTime) {
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
		return new ResponseCookiesVo(sessionCookie, refreshCookie);
	}

}
