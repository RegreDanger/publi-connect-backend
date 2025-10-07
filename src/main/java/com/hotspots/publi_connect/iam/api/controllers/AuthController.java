package com.hotspots.publi_connect.iam.api.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.hotspots.publi_connect.iam.api.dto.RegisterUserRequest;
import com.hotspots.publi_connect.iam.app.UserAuthService;

import lombok.Data;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@Data
public class AuthController {
	private final UserAuthService authService;

	public Mono<ServerResponse> registrer(@RequestBody RegisterUserRequest request) {
		return authService.registerUser(request)
				.flatMap(userSaved -> {
					return ServerResponse.noContent()
						.cookie(userSaved.responseCookies().sessionCookie())
						.cookie(userSaved.responseCookies().refreshCookie())
						.build();
				});
	}

}
