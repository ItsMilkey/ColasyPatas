package com.example.patas_y_colas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "TRATAMIENTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un tratamiento médico aplicado a un perro")
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRATAMIENTO")
    @Schema(description = "Identificador único del tratamiento", example = "1")
    private int idTratamiento;

    @Column(name = "DIAGNOSTICO", nullable = false, length = 255)
    @Schema(description = "Diagnóstico médico que justifica el tratamiento", example = "Otitis aguda")
    private String diagnostico;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_INICIO")
    @Schema(description = "Fecha de inicio del tratamiento", example = "2025-05-01")
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_TERMINO")
    @Schema(description = "Fecha de término del tratamiento", example = "2025-05-10")
    private Date fechaTermino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERRO_ID_PERRO", nullable = false)
    @Schema(description = "Perro asociado al tratamiento")
    private Perro perro;

    // Lógica interna
    public void actualizarDiagnostico(String nuevoDiagnostico) {
        if (nuevoDiagnostico == null || nuevoDiagnostico.trim().isEmpty()) {
            throw new IllegalArgumentException("El diagnóstico no puede estar vacío");
        }
        this.diagnostico = nuevoDiagnostico;
    }

    public int duracionDias() {
        if (fechaInicio == null || fechaTermino == null) {
            return -1;
        }
        long diffInMillis = fechaTermino.getTime() - fechaInicio.getTime();
        return (int) TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    public boolean estaActivo() {
        if (fechaInicio == null || fechaTermino == null) {
            return false;
        }
        Date ahora = new Date();
        return !ahora.before(fechaInicio) && !ahora.after(fechaTermino);
    }
}
