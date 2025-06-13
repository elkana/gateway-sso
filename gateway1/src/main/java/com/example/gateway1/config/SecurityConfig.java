package com.example.gateway1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http.csrf(AbstractHttpConfigurer::disable)
                                .cors(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(r -> r
                                                .requestMatchers("/api/**").authenticated()
                                                // .requestMatchers("/actuator/**").permitAll()
                                                // .requestMatchers("/v3/api-docs/**").permitAll()
                                                .anyRequest().permitAll())
                                .oauth2ResourceServer(o -> o
                                                .jwt(withDefaults()))
                                .oauth2Login(withDefaults())
                                .build();

        }
}
