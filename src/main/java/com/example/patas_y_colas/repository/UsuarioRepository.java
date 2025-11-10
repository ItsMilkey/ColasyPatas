package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Ya no se necesitan 'java.util.List' ni 'java.util.Optional'

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // No necesitamos métodos personalizados para el CRUD básico.
    // JpaRepository ya provee a nuestro UsuarioService de:
    // - findAll()
    // - save(Usuario entity)
    // - deleteById(Long id)
    // - existsById(Long id)
}