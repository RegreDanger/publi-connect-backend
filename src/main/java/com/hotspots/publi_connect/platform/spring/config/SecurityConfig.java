package com.hotspots.publi_connect.platform.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.hotspots.publi_connect.platform.spring.auth.TokenAuthManager;
import com.hotspots.publi_connect.platform.spring.context.StatelessTokenSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final TokenAuthManager tokenAuthManager;
    private final StatelessTokenSecurityContextRepository statelessTokenRepository;

    public SecurityConfig(TokenAuthManager tokenAuthManager,
            StatelessTokenSecurityContextRepository statelessTokenRepository) {
        this.tokenAuthManager = tokenAuthManager;
        this.statelessTokenRepository = statelessTokenRepository;
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                    .csrf(ServerHttpSecurity.CsrfSpec::disable)
                    .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                    .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                    .securityContextRepository(statelessTokenRepository)
                    .authorizeExchange(ex ->
                        ex.pathMatchers("/public/**", "/auth/**").permitAll()
                            .pathMatchers(HttpMethod.POST, "/users").permitAll()
                            .anyExchange().authenticated()
                    )
                    .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessHandler((exchange, authentication) -> {
                            exchange.getExchange().getResponse().getCookies().remove("sessionToken");
                            exchange.getExchange().getResponse().getCookies().remove("refreshToken");
                            return exchange.getExchange().getResponse().setComplete();
                        })
                    )
                    .authenticationManager(tokenAuthManager)
                    .build();
    }

}
