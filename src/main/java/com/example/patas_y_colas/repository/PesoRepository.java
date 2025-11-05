package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Peso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PesoRepository extends JpaRepository<Peso, Integer> {

    // Buscar todos los pesos de un perro por su chipId
    List<Peso> findByPerroChipId(String chipId);

    // Buscar los pesos de un perro por su chipId ordenados por fecha descendente (del más reciente al más antiguo)
    List<Peso> findByPerroChipIdOrderByFechaDesc(String chipId);

    // Buscar pesos por fecha específica
    List<Peso> findByFecha(Date fecha);
}