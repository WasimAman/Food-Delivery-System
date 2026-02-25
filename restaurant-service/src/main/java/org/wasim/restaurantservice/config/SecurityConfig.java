package org.wasim.restaurantservice.config;

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
import org.wasim.restaurantservice.security.HeaderAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {
    // Security Filter Chain Bean
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // ADMIN Only
                        .requestMatchers(HttpMethod.PUT,
                                "/api/restaurants/*/approve")
                        .hasAuthority("ROLE_ADMIN")

                        // RESTAURANT Only
                        .requestMatchers(HttpMethod.POST,
                                "/api/restaurants")
                        .hasAuthority("ROLE_RESTAURANT")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/restaurants/*")
                        .hasAnyAuthority("ROLE_RESTAURANT","ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.GET,
//                                "/api/restaurants/*/summary")
//                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_CUSTOMER")
                        // Public Access (Browse)
                        .requestMatchers(HttpMethod.GET,
                                "/api/restaurants/**")
                        .permitAll()

                        .anyRequest().permitAll()
                )
                .addFilterBefore(new HeaderAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS Configuration
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
