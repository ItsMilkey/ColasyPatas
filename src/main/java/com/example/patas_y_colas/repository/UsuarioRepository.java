package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // 1. IMPORTACIÓN AÑADIDA

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // 2. MÉTODO AÑADIDO
    /**
     * Busca un usuario por su dirección de email.
     * Spring Data JPA creará la consulta automáticamente.
     * Es necesario para el UserDetailsService de Spring Security.
     */
    Optional<Usuario> findByEmail(String email);

}