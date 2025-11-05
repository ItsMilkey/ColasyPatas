package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    List<Usuario> findByRol(String rol);
    Optional<Usuario> findByEmail(String email);
}
