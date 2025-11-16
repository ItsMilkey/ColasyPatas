package com.example.patas_y_colas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated; // 1. IMPORTACIÓN AÑADIDA
import jakarta.persistence.EnumType;   // 2. IMPORTACIÓN AÑADIDA
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// --- 3. IMPORTACIONES DE SPRING SECURITY AÑADIDAS ---
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; 

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails { // 4. IMPLEMENTAMOS USERDETAILS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 255) // 5. LARGO CAMBIADO A 255 (para encriptación)
    private String password;

    // --- 6. CAMPO DE ROL AÑADIDO ---
    @Enumerated(EnumType.STRING) // Le dice a JPA que guarde el nombre del Enum (ej. "ROLE_ADMIN")
    @Column(name = "ROLE", nullable = false, length = 20)
    private Role role;


    // --- 7. MÉTODOS REQUERIDOS POR UserDetails ---
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devuelve el rol del usuario
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // Usamos el email como username
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Asumimos que la cuenta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Asumimos que la cuenta nunca se bloquea
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Asumimos que las credenciales nunca expiran
    }

    @Override
    public boolean isEnabled() {
        return true; // Asumimos que la cuenta está siempre habilitada
    }
}