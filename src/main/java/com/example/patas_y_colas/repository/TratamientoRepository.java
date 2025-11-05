package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Integer> {

    // Buscar todos los tratamientos de un perro específico por su chipId
    List<Tratamiento> findByPerroChipId(String chipId);

    // Buscar tratamientos activos (iniciados antes o el día de hoy y no terminados)
    List<Tratamiento> findByFechaInicioLessThanEqualAndFechaTerminoGreaterThanEqual(Date desde, Date hasta);

    // Buscar tratamientos por diagnóstico exacto
    List<Tratamiento> findByDiagnostico(String diagnostico);

    // Buscar tratamientos por diagnóstico ignorando mayúsculas/minúsculas
    List<Tratamiento> findByDiagnosticoIgnoreCase(String diagnostico);
}