package org.wasim.orderservice.config;

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
import org.wasim.orderservice.security.HeaderAuthenticationFilter;

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
                        // CREATE ORDER → USER ONLY
                        // =========================
                        .requestMatchers(HttpMethod.POST, "/api/orders")
                        .hasAuthority("ROLE_CUSTOMER")

                        // =========================
                        // MY ORDERS → USER ONLY
                        // =========================
                        .requestMatchers(HttpMethod.GET, "/api/orders/my-orders")
                        .hasAuthority("ROLE_CUSTOMER")

                        // =========================
                        // UPDATE ORDER STATUS → RESTAURANT / ADMIN
                        // =========================
                        .requestMatchers(HttpMethod.PUT, "/api/orders/*/status")
                        .hasAnyAuthority("ROLE_RESTAURANT", "ROLE_ADMIN")

                        // =========================
                        // GET SINGLE ORDER → USER / ADMIN
                        // =========================
                        .requestMatchers(HttpMethod.GET, "/api/orders/*")
                        .hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN")

                        // Any other order endpoint
//                        .requestMatchers("/api/orders/**")
//                        .authenticated()

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

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}