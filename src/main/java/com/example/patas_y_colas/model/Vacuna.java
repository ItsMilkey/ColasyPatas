package com.example.patas_y_colas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "VACUNA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una vacuna de mascota")
public class Vacuna {

    @Id
    @Column(name = "ID_VACUNA", unique = true)
    @Schema(description = "Identificador único de la vacuna", example = "VAC001")
    private String idVacuna;

    @Column(name = "NOMBRE_VACUNA", nullable = false)
    @Schema(description = "Nombre de la vacuna", example = "Antirrábica")
    private String nombre;

    @Column(name = "DESCRIPCION")
    @Schema(description = "Descripción de la vacuna", example = "Vacuna contra la rabia para perros y gatos")
    private String descripcion;

    @Column(name = "OBLIGATORIA")
    @Schema(description = "Indica si la vacuna es obligatoria", example = "true")
    private boolean obligatoria;

    public boolean esObligatoria() {
        return obligatoria;
    }

    public void actualizarDescripcion(String nuevaDescripcion) {
        this.descripcion = nuevaDescripcion;
    }
}
