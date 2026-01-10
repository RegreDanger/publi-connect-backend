package com.hotspots.publi_connect.iam.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.hotspots.publi_connect.iam.api.dto.auth.RegisterPersonalAccountRequest;
import com.hotspots.publi_connect.iam.app.UserAuthService;
import com.hotspots.publi_connect.kernel.utils.mappers.RegisterPersonalAccountReqMapper;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/auth")
@Validated
@Data
public class AuthController {
	private final UserAuthService authService;
	private final RegisterPersonalAccountReqMapper registerUserMapper;

	@PostMapping("personal/register")
	public Mono<Void> register(@Valid @RequestBody Mono<RegisterPersonalAccountRequest> request, ServerWebExchange exchange) {
		return request.flatMap(req -> {
			ServerHttpResponse response = exchange.getResponse();
			return authService.registerPersonalAccount(registerUserMapper.toValidatedRequest(req))
				.flatMap(userSaved -> {
					response.addCookie(userSaved.responseCookies().sessionCookie());
					response.addCookie(userSaved.responseCookies().refreshCookie());
					response.setStatusCode(HttpStatus.NO_CONTENT);
					return response.setComplete();
				});
		});
	}

}
