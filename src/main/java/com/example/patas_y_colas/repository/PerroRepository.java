package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Perro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerroRepository extends JpaRepository<Perro, String> {

    // Buscar perros por raza
    List<Perro> findByRazaIgnoreCase(String raza);

    // Verificar si existe un perro por su chipId
    boolean existsByChipId(String chipId);
}