package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.dtos.VacunacionDTO;
import com.example.patas_y_colas.model.Vacunacion;
import com.example.patas_y_colas.service.VacunacionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/vacunaciones")
@Tag(name = "Vacunaciones", description = "Operaciones relacionadas con el control de vacunaciones de perros")
public class VacunacionController {

    @Autowired
    private VacunacionService vacunacionService;

    @GetMapping
    @Operation(summary = "Listar todas las vacunaciones", description = "Obtiene una lista con todas las vacunaciones registradas")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Vacunacion.class))))
    public ResponseEntity<List<Vacunacion>> listarVacunaciones() {
        List<Vacunacion> lista = vacunacionService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener vacunación por ID", description = "Devuelve una vacunación específica según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vacunación encontrada",
            content = @Content(schema = @Schema(implementation = Vacunacion.class))),
        @ApiResponse(responseCode = "404", description = "Vacunación no encontrada")
    })
    public ResponseEntity<Vacunacion> obtenerVacunacion(
        @Parameter(description = "ID de la vacunación", required = true) @PathVariable String id) {
        try {
            Vacunacion vac = vacunacionService.findById(id);
            return ResponseEntity.ok(vac);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
@Operation(
    summary = "Crear nueva vacunación",
    description = "Registra una nueva vacunación en el sistema",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos de la vacunación a registrar",
        required = true,
        content = @Content(
            schema = @Schema(implementation = VacunacionDTO.class),
            examples = @ExampleObject(
                name = "EjemploVacunacion",
                value = "{\"idVacunacion\": \"VAC123\", \"fechaAplicacion\": \"2025-05-01\", \"fechaProxima\": \"2025-11-01\"}"
            )
        )
    )
)
@ApiResponse(
    responseCode = "200",
    description = "Vacunación registrada exitosamente",
    content = @Content(schema = @Schema(implementation = Vacunacion.class))
)
public ResponseEntity<Vacunacion> crearVacunacion(@RequestBody VacunacionDTO vacunacionDTO) {
    Vacunacion vac = vacunacionService.save(vacunacionDTO);
    return ResponseEntity.ok(vac);
}

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar vacunación", description = "Elimina una vacunación del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vacunación eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Vacunación no encontrada")
    })
    public ResponseEntity<Void> eliminarVacunacion(
        @Parameter(description = "ID de la vacunación a eliminar", required = true) @PathVariable String id) {
        try {
            vacunacionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/vencida")
    @Operation(summary = "Verificar si vacunación está vencida", description = "Devuelve true si la vacunación está vencida")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resultado de verificación retornado",
            content = @Content(schema = @Schema(implementation = Boolean.class))),
        @ApiResponse(responseCode = "404", description = "Vacunación no encontrada")
    })
    public ResponseEntity<Boolean> estaVencida(
        @Parameter(description = "ID de la vacunación", required = true) @PathVariable String id) {
        try {
            boolean vencida = vacunacionService.estaVacunaVencida(id);
            return ResponseEntity.ok(vencida);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/dias-proxima")
    @Operation(summary = "Días para próxima vacuna", description = "Devuelve los días restantes para la siguiente dosis")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Número de días retornado",
            content = @Content(schema = @Schema(implementation = Integer.class))),
        @ApiResponse(responseCode = "404", description = "Vacunación no encontrada")
    })
    public ResponseEntity<Integer> diasParaProxima(
        @Parameter(description = "ID de la vacunación", required = true) @PathVariable String id) {
        try {
            int dias = vacunacionService.diasHastaProximaVacuna(id);
            return ResponseEntity.ok(dias);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
