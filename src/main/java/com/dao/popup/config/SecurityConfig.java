package com.dao.popup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
public class SecurityConfig {

    @SuppressWarnings("deprecation")
    @Bean
    protected DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(requests -> requests
                        .anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .headers(headers ->
                // xss 헤더 구성
                headers.xssProtection(
                        xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                ).contentSecurityPolicy(
                        // 스크립트를 허용하지 못하도록 block
                        cps -> cps.policyDirectives("script-src 'self'")
                ));
        return http.build();
    }


}
