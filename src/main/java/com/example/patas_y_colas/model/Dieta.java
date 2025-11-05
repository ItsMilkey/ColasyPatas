package com.example.patas_y_colas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "DIETA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una dieta asignada a un perro")
public class Dieta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DIETA")
    @Schema(description = "Identificador único de la dieta", example = "1")
    private int idDieta;

    @Column(name = "DESCRIPCION", length = 500)
    @Schema(description = "Descripción detallada de la dieta", example = "Dieta rica en proteínas y baja en grasas.")
    private String descripcion;

    @Column(name = "FECHA_INICIO")
    @Schema(description = "Fecha de inicio de la dieta", example = "2024-05-01")
    private LocalDate fechaInicio;

    @Column(name = "FECHA_TERMINO")
    @Schema(description = "Fecha de término de la dieta", example = "2024-06-01")
    private LocalDate fechaTermino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERRO_ID_PERRO", nullable = false)
    @Schema(description = "Perro al que está asignada esta dieta")
    private Perro perro;

    // Métodos auxiliares
    public boolean dietaActiva() {
        LocalDate hoy = LocalDate.now();
        return (fechaInicio != null && fechaTermino != null)
                && (!hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaTermino));
    }

    public boolean estaVigente() {
        return dietaActiva();
    }

    public void actualizarDescripcion(String nuevaDescripcion) {
        this.descripcion = nuevaDescripcion;
    }
}
