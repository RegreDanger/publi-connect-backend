package com.hotspots.publi_connect.platform.spring.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.hotspots.publi_connect.platform.spring.auth.TokenAuthManager;
import com.hotspots.publi_connect.platform.spring.context.StatelessTokenSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${cors.allowed-origins:http://localhost:5173}")
    private String allowedOrigins;

    @Value("${cookies.secure:true}")
    private boolean cookieSecure;

    @Value("${cookies.same-site:None}")
    private String cookieSameSite;

    private final TokenAuthManager tokenAuthManager;
    private final StatelessTokenSecurityContextRepository statelessTokenRepository;

    public SecurityConfig(TokenAuthManager tokenAuthManager,
            StatelessTokenSecurityContextRepository statelessTokenRepository) {
        this.tokenAuthManager = tokenAuthManager;
        this.statelessTokenRepository = statelessTokenRepository;
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        CookieServerCsrfTokenRepository csrfRepo = CookieServerCsrfTokenRepository.withHttpOnlyFalse();
        csrfRepo.setCookieName("XSRF-TOKEN");
        csrfRepo.setCookieCustomizer(cookie -> cookie.sameSite(cookieSameSite).secure(cookieSecure));
        ServerCsrfTokenRequestAttributeHandler csrfRequestHandler = new ServerCsrfTokenRequestAttributeHandler();

        return http
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfRepo)
                        .csrfTokenRequestHandler(csrfRequestHandler)
                    )
                    .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                    .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                    .securityContextRepository(statelessTokenRepository)
                    .authorizeExchange(ex ->
                        ex.pathMatchers("/public/**", "/auth/**", "/videos/**").permitAll()
                            .pathMatchers(HttpMethod.POST, "/users").permitAll()
                            .anyExchange().authenticated()
                    )
                    .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessHandler((exchange, authentication) -> {
                            ServerHttpResponse response = exchange.getExchange().getResponse();

                            ResponseCookie killSession = ResponseCookie.from("session_token", "")
                                                            .maxAge(0).path("/").build();
                    
                            ResponseCookie killRefresh = ResponseCookie.from("refresh_token", "")
                                                            .maxAge(0).path("/").build();

                            response.addCookie(killSession);
                            response.addCookie(killRefresh);
                            
                            return response.setComplete();
                        })
                    )
                    .authenticationManager(tokenAuthManager)
                    .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(resolveAllowedOrigins());
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); 
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private List<String> resolveAllowedOrigins() {
        return Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .collect(Collectors.toList());
    }

}
