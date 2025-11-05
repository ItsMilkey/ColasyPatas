package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Alimentacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlimentacionRepository extends JpaRepository<Alimentacion, Integer> {

    // Buscar todas las alimentaciones de un perro específico por su chipId
    List<Alimentacion> findByPerroChipId(String chipId);

    // Buscar alimentaciones que coincidan con una porción exacta
    List<Alimentacion> findByPorcion(String porcion);
}