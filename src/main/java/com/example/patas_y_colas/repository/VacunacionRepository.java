package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Vacunacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VacunacionRepository extends JpaRepository<Vacunacion, String> {

    // Buscar vacunaciones por chipId de perro
    List<Vacunacion> findByPerroChipId(String chipId);

    // Buscar vacunaciones por chipId de perro y vacunas vencidas
    List<Vacunacion> findByPerroChipIdAndFechaProximaBefore(String chipId, LocalDate fecha);

    // Buscar vacunaciones por chipId de perro y vacunas no vencidas (fechaProxima después o igual a hoy)
    List<Vacunacion> findByPerroChipIdAndFechaProximaGreaterThanEqual(String chipId, LocalDate fecha);
}