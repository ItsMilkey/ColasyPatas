package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.TipoComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoComidaRepository extends JpaRepository<TipoComida, Integer> {

    // Buscar todos los tipos de comida de un perro específico por chipId
    List<TipoComida> findByPerroChipId(String chipId);

    // Buscar tipos de comida por nombre exacto
    List<TipoComida> findByNombre(String nombre);

    // Buscar tipos de comida por nombre ignorando mayúsculas y minúsculas
    List<TipoComida> findByNombreIgnoreCase(String nombre);
}