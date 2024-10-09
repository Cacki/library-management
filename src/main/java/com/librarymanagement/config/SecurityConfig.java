package com.librarymanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**") // Disable CSRF for H2 console
        ).authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console
                .anyRequest().permitAll() // Allow all other requests
        ).headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)); // Allow H2 console in frames

        return http.build();
    }
}
