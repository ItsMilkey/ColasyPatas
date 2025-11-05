package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Integer> {

    // Buscar historial médico de un perro por su chipId
    HistorialMedico findByPerroChipId(String chipId);
}