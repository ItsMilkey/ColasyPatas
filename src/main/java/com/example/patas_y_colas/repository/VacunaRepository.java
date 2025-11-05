package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacunaRepository extends JpaRepository<Vacuna, String> {

    // Buscar vacunas por nombre exacto
    List<Vacuna> findByNombre(String nombre);

    // Buscar vacunas por nombre ignorando mayúsculas y minúsculas
    List<Vacuna> findByNombreIgnoreCase(String nombre);

    // Buscar vacunas obligatorias o no
    List<Vacuna> findByObligatoria(boolean obligatoria);
}