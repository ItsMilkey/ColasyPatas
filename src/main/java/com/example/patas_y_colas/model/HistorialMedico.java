package com.example.patas_y_colas.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "HISTORIAL_MEDICO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa el historial médico de un perro")
public class HistorialMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HISTORIAL")
    @Schema(description = "Identificador único del historial médico", example = "1")
    private int idHistorial;

    @Column(name = "CONSULTAS")
    @Schema(description = "Consultas médicas registradas en formato separado por punto y coma (;)", example = "Consulta general;Vacunación;Desparasitación")
    private String consultas;

    @ElementCollection
    @CollectionTable(name = "DIAGNOSTICOS", joinColumns = @JoinColumn(name = "HISTORIAL_ID"))
    @Column(name = "DIAGNOSTICO")
    @ArraySchema(schema = @Schema(description = "Diagnóstico médico registrado"), arraySchema = @Schema(description = "Lista de diagnósticos médicos"))
    private List<String> diagnosticos;

    @ElementCollection
    @CollectionTable(name = "PESOS_HISTORIA", joinColumns = @JoinColumn(name = "HISTORIAL_ID"))
    @Column(name = "PESO")
    @ArraySchema(schema = @Schema(description = "Peso en kilogramos"), arraySchema = @Schema(description = "Lista de pesos registrados del perro"))
    private List<Float> pesos;

    @Column(name = "FECHA_CONSULTA")
    @Schema(description = "Fecha de la última consulta médica", example = "2024-05-31")
    private Date fechaConsulta;

    @OneToOne
    @JoinColumn(name = "PERRO_ID_PERRO", referencedColumnName = "ID_PERRO", nullable = false)
    @Schema(description = "Referencia al perro asociado a este historial médico")
    private Perro perro;

    // Métodos adicionales (no afectan Swagger)
    public String ultimoDiagnostico() {
        if (diagnosticos == null || diagnosticos.isEmpty()) return "Sin diagnóstico";
        return diagnosticos.get(diagnosticos.size() - 1);
    }

    public float calcularPromedioPeso() {
        if (pesos == null || pesos.isEmpty()) return 0f;
        float suma = 0f;
        for (Float peso : pesos) suma += peso;
        return suma / pesos.size();
    }

    public List<String> listarConsultas() {
        return List.of(consultas.split(";"));
    }

    public List<String> listarDiagnosticos() {
        return diagnosticos != null ? diagnosticos : List.of();
    }
}
