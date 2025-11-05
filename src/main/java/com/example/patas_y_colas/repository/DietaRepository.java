package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DietaRepository extends JpaRepository<Dieta, Integer> {

    // Buscar todas las dietas asociadas a un perro específico
    List<Dieta> findByPerroChipId(String chipId);

    // Buscar dietas activas por perro entre dos fechas
    List<Dieta> findByPerroChipIdAndFechaInicioLessThanEqualAndFechaTerminoGreaterThanEqual(
        String chipId, LocalDate desde, LocalDate hasta
    );
}