package com.internship.infosys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =====================================================
    // ACCOUNT STATUS
    // =====================================================

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(nullable = false)
    private boolean emailVerified = false;

    // =====================================================
    // USER INFORMATION
    // =====================================================

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String department;

    // =====================================================
    // ROLE
    // =====================================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    // =====================================================
    // DEFAULT CONSTRUCTOR
    // =====================================================

    public User() {
    }

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public User(
            Long id,
            String username,
            String email,
            String password,
            String department,
            Role role) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.department = department;
        this.role = role;
    }
}