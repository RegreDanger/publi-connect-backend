package com.hotspots.publi_connect.iam.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.app.output.CreateSessionResult;
import com.hotspots.publi_connect.iam.vo.UUIDVo;
import com.hotspots.publi_connect.platform.spring.config.JwtConfig;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class AuthService {
	private final TokenService jwt;
	private final JwtConfig jwtConfig;

	@Value("${cookies.secure:true}")
	private boolean cookieSecure;

	@Value("${cookies.same-site:None}")
	private String cookieSameSite;

	public Mono<CreateSessionResult> createSession(@NotNull @Valid UUIDVo accountIdVo) {

		return Mono.just(accountIdVo).map(accountIdVoMapping -> {
			String refreshToken = jwt.generateRefreshToken(accountIdVoMapping);
			String sessionToken = jwt.generateSessionToken(accountIdVoMapping);
			return buildCookies(sessionToken, refreshToken, jwtConfig.getExpiration().getTime(), jwtConfig.getRefreshTime());
		});
	}

	private CreateSessionResult buildCookies(String sessionToken, String refreshToken, long expirationTime, long refreshTime) {
		ResponseCookie sessionCookie = ResponseCookie
												.from("session_token", sessionToken)
												.httpOnly(true)
												.secure(cookieSecure)
												.path("/")
												.maxAge(expirationTime/1000)
												.sameSite(cookieSameSite)
												.build();
		ResponseCookie refreshCookie = ResponseCookie
												.from("refresh_token", refreshToken)
												.httpOnly(true)
												.secure(cookieSecure)
												.path("/")
												.maxAge(refreshTime/1000)
												.sameSite(cookieSameSite)
												.build();
		return new CreateSessionResult(sessionCookie, refreshCookie);
	}

}
