package com.example.patas_y_colas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "TIPO_COMIDA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un tipo de comida asociada a un perro")
public class TipoComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_COMIDA")
    @Schema(description = "Identificador único del tipo de comida", example = "1")
    private int idTipo;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    @Schema(description = "Nombre del tipo de comida", example = "Croquetas premium para cachorros")
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERRO_ID_PERRO", nullable = false)
    @Schema(description = "Perro al que está asociado este tipo de comida")
    private Perro perro;
}
