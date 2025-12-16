package com.example.patas_y_colas.repository;

import com.example.patas_y_colas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Método mágico de JPA para buscar por ID de usuario ordenado por fecha
    List<Venta> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
}