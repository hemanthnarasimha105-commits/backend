package com.internship.infosys.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.internship.infosys.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // =====================================================
    // SECURITY FILTER CHAIN
    // =====================================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                // =================================================
                // CORS
                // =================================================
                .cors(Customizer.withDefaults())

                // =================================================
                // CSRF
                // =================================================
                .csrf(csrf -> csrf.disable())

                // =================================================
                // STATELESS SESSION
                // =================================================
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))

                // =================================================
                // AUTHORIZATION
                // =================================================
                .authorizeHttpRequests(auth -> auth

                        // =================================================
                        // CORS PREFLIGHT
                        // =================================================
                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**")
                        .permitAll()

                        // =================================================
                        // AUTHENTICATION
                        // =================================================
                        .requestMatchers(
                                "/api/auth/**")
                        .permitAll()

                        // =================================================
                        // SWAGGER
                        // =================================================
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**")
                        .permitAll()

                        // =================================================
                        // AI CHAT
                        // =================================================
                        .requestMatchers(
                                "/api/chat/**")
                        .authenticated()

                        // =================================================
                        // AI ASSISTANT
                        // =================================================
                        .requestMatchers(
                                "/api/ai/**")
                        .authenticated()

                        // =================================================
                        // ASSETS
                        // =================================================

                        // GET -> ADMIN, ITSM, USER
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/assets/**")
                        .hasAnyRole(
                                "ADMIN",
                                "ITSM",
                                "USER")

                        // POST -> ADMIN, ITSM
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/assets/**")
                        .hasAnyRole(
                                "ADMIN",
                                "ITSM")

                        // PUT -> ADMIN, ITSM
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/assets/**")
                        .hasAnyRole(
                                "ADMIN",
                                "ITSM")

                        // PATCH -> ADMIN, ITSM
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/assets/**")
                        .hasAnyRole(
                                "ADMIN",
                                "ITSM")

                        // DELETE -> ADMIN ONLY
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/assets/**")
                        .hasRole("ADMIN")

                        // =================================================
                        // DASHBOARD
                        // =================================================
                        .requestMatchers(
                                "/api/dashboard/**")
                        .authenticated()

                        // =================================================
                        // USERS
                        // =================================================

                        // GET -> ADMIN, ITSM, USER
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/users/**")
                        .hasAnyRole(
                                "ADMIN",
                                "ITSM",
                                "USER")

                        // POST -> ADMIN ONLY
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/users/**")
                        .hasRole("ADMIN")

                        // PUT -> ADMIN ONLY
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/users/**")
                        .hasRole("ADMIN")

                        // PATCH -> ADMIN ONLY
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/users/**")
                        .hasRole("ADMIN")

                        // DELETE -> ADMIN ONLY
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/users/**")
                        .hasRole("ADMIN")

                        // =================================================
                        // REPORTS
                        // =================================================
                        .requestMatchers(
                                "/api/reports/**")
                        .authenticated()

                        // =================================================
                        // ALERTS
                        // =================================================
                        .requestMatchers(
                                "/api/alerts/**")
                        .authenticated()

                        // =================================================
                        // CLOUD
                        // =================================================
                        .requestMatchers(
                                "/api/cloud/**")
                        .authenticated()

                        // =================================================
                        // INCIDENTS
                        // =================================================
                        .requestMatchers(
                                "/api/incidents/**")
                        .authenticated()

                        // =================================================
                        // VULNERABILITIES
                        // =================================================
                        .requestMatchers(
                                "/api/vulnerabilities/**")
                        .authenticated()

                        // =================================================
                        // EVERYTHING ELSE
                        // =================================================
                        .anyRequest()
                        .authenticated())

                // =================================================
                // JWT FILTER
                // =================================================
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // =====================================================
    // AUTHENTICATION MANAGER
    // =====================================================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    // =====================================================
    // PASSWORD ENCODER
    // =====================================================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // =====================================================
    // CORS CONFIGURATION
    // =====================================================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // =================================================
        // FRONTEND
        // =================================================

        config.setAllowedOrigins(
                List.of(
                        "https://frontend-1xoh.onrender.com"));

        // =================================================
        // METHODS
        // =================================================

        config.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"));

        // =================================================
        // HEADERS
        // =================================================

        config.setAllowedHeaders(
                List.of("*"));

        // =================================================
        // EXPOSED HEADERS
        // =================================================

        config.setExposedHeaders(
                List.of(
                        "Authorization"));

        // =================================================
        // CREDENTIALS
        // =================================================

        config.setAllowCredentials(true);

        // =================================================
        // REGISTER CORS
        // =================================================

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                config);

        return source;
    }
}