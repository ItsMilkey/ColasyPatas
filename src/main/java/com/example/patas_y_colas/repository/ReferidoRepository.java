package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Referido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferidoRepository extends JpaRepository<Referido, Long> {
    // JpaRepository ya nos da todo lo que necesitamos:
    // findAll(), findById(), save(), deleteById()
}