package com.hotspots.publi_connect.iam.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.hotspots.publi_connect.iam.api.dto.auth.RegisterUserRequest;
import com.hotspots.publi_connect.iam.app.UserRegistrationService;
import com.hotspots.publi_connect.kernel.utils.mappers.RegisterUserRequestMapper;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/users")
@Validated
@Data
public class UserController {
	private final UserRegistrationService userRegistrationService;
	private final RegisterUserRequestMapper registerUserRequestMapper;

	@PostMapping
	public Mono<Void> register(@Valid @RequestBody Mono<RegisterUserRequest> request, ServerWebExchange exchange) {
		return request.flatMap(req -> {
			ServerHttpResponse response = exchange.getResponse();
			return userRegistrationService.registerUser(registerUserRequestMapper.toValidatedRequest(req))
				.flatMap(userSaved -> {
					response.addCookie(userSaved.responseCookies().sessionCookie());
					response.addCookie(userSaved.responseCookies().refreshCookie());
					response.setStatusCode(HttpStatus.NO_CONTENT);
					return response.setComplete();
				});
		});
	}

}
