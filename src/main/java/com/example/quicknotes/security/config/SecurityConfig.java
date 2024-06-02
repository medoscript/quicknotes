package com.example.quicknotes.security.config;

import com.example.quicknotes.security.sec_filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private TokenFilter filter;

    public SecurityConfig(TokenFilter filter) {
        this.filter = filter;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // Конфигурация для JWT авторизации
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x -> x
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(x -> x
                        .requestMatchers(HttpMethod.GET, "/tasks/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/tasks").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/tasks/", "/files").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/access", "/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/hello").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll())
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}