package com.internship.infosys.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    // =====================================================
    // JWT FILTER
    // =====================================================

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // =================================================
        // REQUEST PATH
        // =================================================

        String path =
                request.getServletPath();

        // =================================================
        // SKIP AUTH ENDPOINTS
        // =================================================

        if (path.startsWith("/api/auth/")) {

            filterChain.doFilter(
                request,
                response
            );

            return;
        }

        // =================================================
        // GET AUTHORIZATION HEADER
        // =================================================

        String authHeader =
                request.getHeader("Authorization");

        String token = null;

        String username = null;

        // =================================================
        // CHECK BEARER TOKEN
        // =================================================

        if (authHeader != null
                && authHeader.startsWith("Bearer ")) {

            token =
                    authHeader.substring(7);

            try {

                username =
                        jwtUtil.extractUsername(token);

            } catch (Exception e) {

                System.out.println(
                    "❌ Invalid JWT: "
                    + e.getMessage()
                );
            }
        }

        // =================================================
        // AUTHENTICATE USER
        // =================================================

        if (username != null
                && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

            try {

                UserDetails userDetails =
                        userDetailsService
                            .loadUserByUsername(
                                username
                            );

                // =========================================
                // DEBUG USER
                // =========================================

                System.out.println(
                    "======================================"
                );

                System.out.println(
                    "🔐 JWT USER: "
                    + userDetails.getUsername()
                );

                System.out.println(
                    "🔐 JWT AUTHORITIES: "
                    + userDetails.getAuthorities()
                );

                System.out.println(
                    "🔐 REQUEST: "
                    + request.getMethod()
                    + " "
                    + request.getRequestURI()
                );

                // =========================================
                // VALIDATE TOKEN
                // =========================================

                boolean validToken =
                        jwtUtil.validateToken(
                            token,
                            userDetails.getUsername()
                        );

                System.out.println(
                    "🔐 JWT VALID: "
                    + validToken
                );

                System.out.println(
                    "======================================"
                );

                // =========================================
                // SET AUTHENTICATION
                // =========================================

                if (validToken) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                            .buildDetails(request)
                    );

                    SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                            authentication
                        );

                    System.out.println(
                        "✅ JWT AUTHENTICATION SUCCESS: "
                        + username
                    );

                } else {

                    System.out.println(
                        "❌ JWT TOKEN INVALID"
                    );
                }

            } catch (Exception e) {

                System.out.println(
                    "❌ JWT authentication failed: "
                    + e.getMessage()
                );
            }
        }

        // =================================================
        // NO TOKEN
        // =================================================

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            System.out.println(
                "⚠️ No Bearer token found for: "
                + request.getRequestURI()
            );
        }

        // =================================================
        // CONTINUE FILTER CHAIN
        // =================================================

        filterChain.doFilter(
            request,
            response
        );
    }
}