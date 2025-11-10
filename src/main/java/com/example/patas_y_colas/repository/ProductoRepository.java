package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // No necesitamos métodos extra.
    // JpaRepository ya nos da findAll(), findById(), save(), deleteById(), etc.
}