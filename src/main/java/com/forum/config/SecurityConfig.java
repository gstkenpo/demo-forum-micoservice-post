package com.forum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable().authorizeExchange()
                    //.pathMatchers(HttpMethod.GET, "/api/post").permitAll()
                    .anyExchange()
                    .authenticated().and()
                    //.addFilter
                    .build()
                    ;
    }

}
