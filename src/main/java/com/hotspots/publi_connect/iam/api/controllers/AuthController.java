package com.hotspots.publi_connect.iam.api.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.hotspots.publi_connect.iam.api.dto.auth.LoginAccountRequest;
import com.hotspots.publi_connect.iam.app.LoginAccountUseCase;
import com.hotspots.publi_connect.kernel.utils.mappers.LoginAccountInputMapper;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@Validated
@Data
public class AuthController {
    private final LoginAccountUseCase loginAccountUseCase;
	private final LoginAccountInputMapper loginAccountInputMapper;

	@PostMapping("/login")
	public Mono<Void> register(@Valid @RequestBody Mono<LoginAccountRequest> request, ServerWebExchange exchange) {
		return request.flatMap(req -> {
			ServerHttpResponse response = exchange.getResponse();
			return loginAccountUseCase.loginAccount(loginAccountInputMapper.toInput(req))
				.flatMap(responseCookies -> {
					response.addCookie(responseCookies.sessionCookie());
					response.addCookie(responseCookies.refreshCookie());
					response.setStatusCode(HttpStatus.NO_CONTENT);
					return response.setComplete();
				});
		});
	}

	@GetMapping("/csrf")
	public Mono<Map<String, String>> csrf(CsrfToken token) {
		return Mono.just(Map.of("csrfToken", token.getToken()));
	}

}
