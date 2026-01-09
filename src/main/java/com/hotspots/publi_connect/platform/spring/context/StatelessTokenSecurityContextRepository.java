package com.hotspots.publi_connect.platform.spring.context;

import java.util.Optional;

import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.hotspots.publi_connect.platform.spring.auth.TokenAuthManager;
import com.hotspots.publi_connect.platform.spring.auth.TokenAuthentication;

import reactor.core.publisher.Mono;

@Component
public class StatelessTokenSecurityContextRepository implements ServerSecurityContextRepository {

    private TokenAuthManager tokenManager;

    public StatelessTokenSecurityContextRepository(TokenAuthManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest httpReq = exchange.getRequest();
        return Mono.justOrEmpty(httpReq.getCookies().getFirst("session_token"))
            .map(HttpCookie::getValue)
            .filter(cookie -> !cookie.isEmpty())
            .flatMap(sessionToken -> {
                String refreshToken = Optional.ofNullable(httpReq.getCookies().getFirst("refresh_token"))
                    .map(HttpCookie::getValue)
                    .orElse("");
                    TokenAuthentication tokenAuth = new TokenAuthentication(sessionToken, refreshToken);
                    return tokenManager.authenticate(tokenAuth).map(SecurityContextImpl::new);
                }
            );
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }
    
}
