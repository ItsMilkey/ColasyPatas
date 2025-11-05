package com.example.patas_y_colas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "PESO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa el peso registrado de un perro en una fecha determinada.")
public class Peso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PESO")
    @Schema(description = "Identificador único del registro de peso", example = "1")
    private int idPeso;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA", nullable = false)
    @Schema(description = "Fecha en que se registró el peso", example = "2025-05-31")
    private Date fecha;

    @Column(name = "PESO_KG", nullable = false)
    @Schema(description = "Peso en kilogramos", example = "12.5")
    private float pesoKg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERRO_ID_PERRO", referencedColumnName = "ID_PERRO", nullable = false)
    @JsonIgnoreProperties({"vacunaciones", "dietas", "pesos", "alimentaciones", "tratamientos", "historialMedico", "vacunas", "tiposComida", "usuario"})
    @Schema(description = "Perro al que corresponde este registro de peso")
    private Perro perro;

    public boolean esPesoNormal(float rangoMin, float rangoMax) {
        return pesoKg >= rangoMin && pesoKg <= rangoMax;
    }
}
