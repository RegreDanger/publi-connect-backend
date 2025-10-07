package com.hotspots.publi_connect.platform.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
public class JwtConfig {
    private Secret secret;
    private Expiration expiration;
    private long refreshTime;

    
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    @Getter
    @Setter
    public static class Secret {
        private String key;
    }
    
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    @Getter
    @Setter
    public static class Expiration {
        private long time;
    }
}

