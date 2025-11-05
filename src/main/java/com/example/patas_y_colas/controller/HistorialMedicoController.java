package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.HistorialMedico;
import com.example.patas_y_colas.service.HistorialMedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historiales-medicos")
@Tag(name = "Historial Médico", description = "Operaciones relacionadas con historiales médicos de perros")
public class HistorialMedicoController {

    private final HistorialMedicoService historialMedicoService;

    @Autowired
    public HistorialMedicoController(HistorialMedicoService historialMedicoService) {
        this.historialMedicoService = historialMedicoService;
    }

    @Operation(summary = "Listar todos los historiales médicos")
    @ApiResponse(responseCode = "200", description = "Lista de historiales encontrada")
    @GetMapping
    public ResponseEntity<List<HistorialMedico>> listarTodos() {
        List<HistorialMedico> historiales = historialMedicoService.findAll();
        return historiales.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(historiales);
    }

    @Operation(summary = "Obtener historial médico por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Historial encontrado"),
        @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedico> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(historialMedicoService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener historial médico por chip del perro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Historial encontrado"),
        @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @GetMapping("/por-perro/{chipId}")
    public ResponseEntity<HistorialMedico> obtenerPorChipPerro(@PathVariable String chipId) {
        HistorialMedico historial = historialMedicoService.findByPerroChipId(chipId);
        return historial != null
                ? ResponseEntity.ok(historial)
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Listar consultas del historial")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Consultas listadas correctamente"),
        @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @GetMapping("/{id}/consultas")
    public ResponseEntity<List<String>> listarConsultas(@PathVariable int id) {
        try {
            return ResponseEntity.ok(historialMedicoService.findById(id).listarConsultas());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar diagnósticos del historial")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Diagnósticos listados correctamente"),
        @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @GetMapping("/{id}/diagnosticos")
    public ResponseEntity<List<String>> listarDiagnosticos(@PathVariable int id) {
        try {
            return ResponseEntity.ok(historialMedicoService.findById(id).listarDiagnosticos());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener promedio de peso registrado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Promedio calculado correctamente"),
        @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @GetMapping("/{id}/promedio-peso")
    public ResponseEntity<Float> obtenerPromedioPeso(@PathVariable int id) {
        try {
            return ResponseEntity.ok(historialMedicoService.calcularPromedioPeso(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener último diagnóstico del historial")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Último diagnóstico obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @GetMapping("/{id}/ultimo-diagnostico")
    public ResponseEntity<String> obtenerUltimoDiagnostico(@PathVariable int id) {
        try {
            return ResponseEntity.ok(historialMedicoService.obtenerUltimoDiagnostico(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo historial médico")
    @ApiResponse(responseCode = "201", description = "Historial creado exitosamente")
    @PostMapping
    public ResponseEntity<HistorialMedico> crearHistorial(@RequestBody HistorialMedico historialMedico) {
        HistorialMedico nuevoHistorial = historialMedicoService.save(historialMedico);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHistorial);
    }

    @Operation(summary = "Actualizar historial médico existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Historial actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedico> actualizarHistorial(@PathVariable int id,
                                                               @RequestBody HistorialMedico historialMedico) {
        try {
            historialMedico.setIdHistorial(id); // asegurar ID coherente
            return ResponseEntity.ok(historialMedicoService.save(historialMedico));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un historial médico")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Historial eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable int id) {
        try {
            historialMedicoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
