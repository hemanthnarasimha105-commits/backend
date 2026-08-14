package com.internship.infosys.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.internship.infosys.model.User;
import com.internship.infosys.repositary.UserRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    // =====================================================
    // LOAD USER BY EMAIL
    // =====================================================

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // =================================================
        // FIND USER
        // =================================================

        User user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email
                        )
                );

        // =================================================
        // VALIDATE ROLE
        // =================================================

        if (user.getRole() == null) {

            throw new UsernameNotFoundException(
                    "User role is not configured for: "
                            + email
            );
        }

        // =================================================
        // CREATE SPRING SECURITY ROLE
        // =================================================

        String role = user.getRole().name();

        String authority = "ROLE_" + role;

        // =================================================
        // DEBUG INFORMATION
        // =================================================

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "🔐 USER LOGIN"
        );

        System.out.println(
                "Email: " + user.getEmail()
        );

        System.out.println(
                "Database Role: " + role
        );

        System.out.println(
                "Spring Security Authority: "
                        + authority
        );

        System.out.println(
                "Enabled: " + user.isEnabled()
        );

        System.out.println(
                "=========================================="
        );

        // =================================================
        // RETURN USER DETAILS
        // =================================================

        return new org.springframework.security.core.userdetails.User(

                // -----------------------------------------
                // USERNAME
                // -----------------------------------------

                user.getEmail(),

                // -----------------------------------------
                // PASSWORD
                // -----------------------------------------

                user.getPassword(),

                // -----------------------------------------
                // ENABLED
                // -----------------------------------------

                user.isEnabled(),

                // -----------------------------------------
                // ACCOUNT NON EXPIRED
                // -----------------------------------------

                true,

                // -----------------------------------------
                // CREDENTIALS NON EXPIRED
                // -----------------------------------------

                true,

                // -----------------------------------------
                // ACCOUNT NON LOCKED
                // -----------------------------------------

                true,

                // -----------------------------------------
                // AUTHORITIES
                // -----------------------------------------

                Collections.singletonList(
                        new SimpleGrantedAuthority(
                                authority
                        )
                )
        );
    }
}