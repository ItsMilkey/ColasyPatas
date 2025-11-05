package com.example.patas_y_colas.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "PERRO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un perro en el sistema veterinario")
public class Perro {

    @Id
    @Column(name = "ID_PERRO", unique = true)
    @Schema(description = "Identificador único del perro (chip)", example = "123ABC456")
    private String chipId;

    @Column(name = "NOMBRE", nullable = false)
    @Schema(description = "Nombre del perro", example = "Firulais")
    private String nombre;

    @Column(name = "RAZA")
    @Schema(description = "Raza del perro", example = "Labrador")
    private String raza;

    @Column(name = "FECHA_NACIMIENTO")
    @Schema(description = "Fecha de nacimiento del perro", example = "2020-05-15")
    private LocalDate fechaNacimiento;

    @Column(name = "GENERO")
    @Schema(description = "Género del perro", example = "macho")
    private String genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID_USUARIO", nullable = false)
    @Schema(description = "Usuario dueño del perro")
    @JsonIgnoreProperties({"perros"}) 
    private Usuario usuario;

    @OneToMany(mappedBy = "perro", cascade = CascadeType.ALL, orphanRemoval = true)
    @ArraySchema(schema = @Schema(implementation = Vacunacion.class))
    private List<Vacunacion> vacunaciones;

    @OneToOne(mappedBy = "perro", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Historial médico del perro")
    private HistorialMedico historialMedico;

    @OneToMany(mappedBy = "perro", cascade = CascadeType.ALL, orphanRemoval = true)
    @ArraySchema(schema = @Schema(implementation = Dieta.class))
    private List<Dieta> dietas;

    @OneToMany(mappedBy = "perro", cascade = CascadeType.ALL, orphanRemoval = true)
    @ArraySchema(schema = @Schema(implementation = Peso.class))
    private List<Peso> pesos;

    @OneToMany(mappedBy = "perro", cascade = CascadeType.ALL, orphanRemoval = true)
    @ArraySchema(schema = @Schema(implementation = Alimentacion.class))
    private List<Alimentacion> alimentaciones;

    @OneToMany(mappedBy = "perro", cascade = CascadeType.ALL, orphanRemoval = true)
    @ArraySchema(schema = @Schema(implementation = Tratamiento.class))
    private List<Tratamiento> tratamientos;

    @ManyToMany
    @JoinTable(
        name = "PERRO_VACUNA",
        joinColumns = @JoinColumn(name = "ID_PERRO"),
        inverseJoinColumns = @JoinColumn(name = "ID_VACUNA")
    )
    @ArraySchema(schema = @Schema(implementation = Vacuna.class))
    private List<Vacuna> vacunas;

    @ManyToMany
    @JoinTable(
        name = "PERRO_TIPO_COMIDA",
        joinColumns = @JoinColumn(name = "ID_PERRO"),
        inverseJoinColumns = @JoinColumn(name = "ID_TIPO_COMIDA")
    )
    @ArraySchema(schema = @Schema(implementation = TipoComida.class))
    private List<TipoComida> tiposComida;

    public int getEdad() {
        if (fechaNacimiento == null) return -1;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public boolean esMacho() {
        return genero != null && genero.equalsIgnoreCase("macho");
    }

    public void agregarPeso(Peso peso) {
        pesos.add(peso);
        peso.setPerro(this);
    }

    public void registrarVacuna(Vacunacion vacunacion) {
        vacunaciones.add(vacunacion);
        vacunacion.setPerro(this);
    }

    public void agregarHistorial(HistorialMedico historial) {
        this.historialMedico = historial;
        historial.setPerro(this);
    }

    public void agregarDieta(Dieta dieta) {
        dietas.add(dieta);
        dieta.setPerro(this);
    }

    public void agregarAlimentacion(Alimentacion alimentacion) {
        alimentaciones.add(alimentacion);
        alimentacion.setPerro(this);
    }

    public void agregarTratamiento(Tratamiento tratamiento) {
        tratamientos.add(tratamiento);
        tratamiento.setPerro(this);
    }

    public Peso getUltimoPeso() {
        if (pesos == null || pesos.isEmpty()) return null;
        return pesos.stream()
            .max(Comparator.comparing(Peso::getFecha))
            .orElse(null);
    }

    public boolean necesitaVacunarse() {
        if (vacunaciones == null || vacunaciones.isEmpty()) return false;
        return vacunaciones.stream().anyMatch(Vacunacion::estaVencida);
    }

    public Vacunacion getProximaVacuna() {
        if (vacunaciones == null) return null;
        return vacunaciones.stream()
            .filter(v -> !v.estaVencida())
            .min(Comparator.comparingInt(Vacunacion::diasParaProxima))
            .orElse(null);
    }
}
