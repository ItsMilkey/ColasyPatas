package com.example.patas_y_colas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "VACUNACION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una vacunación aplicada a un perro")
public class Vacunacion {

    @Id
    @Column(name = "ID_VACUNACION", unique = true, nullable = false)
    @Schema(description = "ID único de la vacunación", example = "VAC123")
    private String idVacunacion;

    @Column(name = "FECHA_APLICACION", nullable = false)
    @Schema(description = "Fecha en la que se aplicó la vacuna", example = "2025-05-01")
    private LocalDate fechaAplicacion;

    @Column(name = "FECHA_PROXIMA")
    @Schema(description = "Fecha programada para la próxima dosis", example = "2025-11-01")
    private LocalDate fechaProxima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERRO_ID", nullable = false)
    @Schema(description = "Perro al que se le aplicó la vacuna")
    private Perro perro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERRO_ID_PERRO", nullable = false)
    @Schema(description = "Vacuna que fue aplicada")
    private Vacuna vacuna;

    public boolean estaVencida() {
        return fechaProxima != null && fechaProxima.isBefore(LocalDate.now());
    }

    public int diasParaProxima() {
        if (fechaProxima == null) return -1;
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), fechaProxima);
    }
}
