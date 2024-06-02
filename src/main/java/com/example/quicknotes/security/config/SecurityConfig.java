package com.example.quicknotes.security.config;


import com.example.quicknotes.security.sec_filter.TokenFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
                        .requestMatchers(HttpMethod.POST, "/tasks", "/files").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/access", "/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/hello").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
//                .anyRequest().permitAll())
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
@Bean
public OpenAPI openAPI() {
    return new OpenAPI().addSecurityItem(new SecurityRequirement().
                    addList("Bearer Authentication"))
            .components(new Components().addSecuritySchemes
                    ("Bearer Authentication", createAPIKeyScheme()))
            .info(new Info().title("JWT demo app")
                    .description("Demo application for JSON web tokens")
                    .version("1.0.0").contact(new Contact().name("Mihail Romanciuc")
                            .email( "roman.mihail@yahoo.com").url("https://www.ait-tr.de/"))
                    .license(new License().name("@MihailRomanciuc")
                            .url("https://www.ait-tr.de/")));
}

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

}