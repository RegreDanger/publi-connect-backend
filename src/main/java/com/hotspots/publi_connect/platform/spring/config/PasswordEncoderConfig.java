package com.hotspots.publi_connect.platform.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
    
    @Bean
    PasswordEncoder bCrypt() {
        return new BCryptPasswordEncoder();
    }
}
