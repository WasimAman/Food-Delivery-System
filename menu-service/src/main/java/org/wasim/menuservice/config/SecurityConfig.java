package org.wasim.menuservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.wasim.menuservice.security.HeaderAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // CREATE MENU → RESTAURANT only
                        // =========================
                        .requestMatchers(HttpMethod.POST, "/api/menus")
                        .hasAuthority("ROLE_RESTAURANT")

                        // =========================
                        // UPDATE MENU
                        // =========================
                        .requestMatchers(HttpMethod.PUT, "/api/menus/*")
                        .hasAnyAuthority("ROLE_RESTAURANT", "ROLE_ADMIN")

                        // =========================
                        // UPDATE AVAILABILITY
                        // =========================
                        .requestMatchers(HttpMethod.PUT, "/api/menus/*/availability")
                        .hasAnyAuthority("ROLE_RESTAURANT", "ROLE_ADMIN")

                        // =========================
                        // PUBLIC VIEW
                        // =========================
                        .requestMatchers(HttpMethod.GET, "/api/menus/restaurant/**")
                        .permitAll()

                        // =========================
                        // Everything else → authenticated
                        // =========================
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new HeaderAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}