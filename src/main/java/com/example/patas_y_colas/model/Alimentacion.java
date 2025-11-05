package com.example.patas_y_colas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.time.ZoneId;

@Entity
@Table(name = "ALIMENTACION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un horario de alimentación para un perro")
public class Alimentacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALIMENTACION")
    @Schema(description = "Identificador único de la alimentación", example = "1")
    private int idAlimentacion;

    @Column(name = "PORCION", nullable = false, length = 50)
    @Schema(description = "Cantidad o tipo de porción", example = "1 taza de croquetas")
    private String porcion;

    @Temporal(TemporalType.TIME)
    @Column(name = "HORARIO", nullable = false)
    @Schema(description = "Hora del día en que se alimenta al perro", type = "string", example = "08:00:00")
    private Date horario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERRO", nullable = false)
    @Schema(description = "Perro asociado a esta alimentación")
    private Perro perro;

    // Métodos auxiliares
    public void actualizarPorcion(String porcion) {
        if (porcion == null || porcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La porción no puede estar vacía");
        }
        this.porcion = porcion;
    }

    public void cambiarHorario(Date horario) {
        if (horario == null) {
            throw new IllegalArgumentException("El horario no puede ser nulo");
        }
        this.horario = horario;
    }

    public boolean esHorarioActual() {
        if (this.horario == null) return false;

        LocalTime ahora = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        LocalTime horaAlimentacion = this.horario.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .truncatedTo(ChronoUnit.MINUTES);

        return ahora.equals(horaAlimentacion);
    }

    public LocalTime obtenerHoraAlimentacion() {
        if (this.horario == null) return null;

        return this.horario.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
    }
}
