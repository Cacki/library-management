package com.librarymanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF protection and allow access to the H2 console
        http.csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection (for H2 console only)
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console
                        .anyRequest().authenticated() // Secure all other requests
                ).headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)); // Disable X-Frame-Options to allow H2 Console in frames

        return http.build();
    }
}
