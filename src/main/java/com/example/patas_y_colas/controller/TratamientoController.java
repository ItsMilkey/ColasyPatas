package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.Tratamiento;
import com.example.patas_y_colas.service.TratamientoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tratamientos")
@Tag(name = "Tratamientos", description = "Operaciones relacionadas con tratamientos médicos aplicados a perros")
public class TratamientoController {

    @Autowired
    private TratamientoService tratamientoService;

    @GetMapping
    @Operation(summary = "Listar todos los tratamientos", description = "Devuelve una lista de todos los tratamientos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de tratamientos",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tratamiento.class)))
    public List<Tratamiento> obtenerTodos() {
        return tratamientoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tratamiento por ID", description = "Devuelve los datos de un tratamiento específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tratamiento encontrado",
            content = @Content(schema = @Schema(implementation = Tratamiento.class))),
        @ApiResponse(responseCode = "404", description = "Tratamiento no encontrado")
    })
    public Tratamiento obtenerPorId(@Parameter(description = "ID del tratamiento") @PathVariable int id) {
        return tratamientoService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo tratamiento", description = "Crea un nuevo tratamiento para un perro")
    @ApiResponse(responseCode = "200", description = "Tratamiento creado exitosamente",
        content = @Content(schema = @Schema(implementation = Tratamiento.class)))
    public Tratamiento guardar(
        @RequestBody(description = "Datos del tratamiento a registrar", required = true,
            content = @Content(schema = @Schema(implementation = Tratamiento.class),
                examples = @ExampleObject(value = "{\"diagnostico\": \"Otitis\", \"fechaInicio\": \"2025-05-01\", \"fechaTermino\": \"2025-05-10\", \"perro\": {\"idPerro\": 1}}")
            )
        )
        @org.springframework.web.bind.annotation.RequestBody Tratamiento tratamiento) {
        return tratamientoService.save(tratamiento);
    }

    @PutMapping
    @Operation(summary = "Actualizar tratamiento", description = "Modifica los datos de un tratamiento existente")
    public Tratamiento actualizar(@RequestBody Tratamiento tratamiento) {
        return tratamientoService.update(tratamiento);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tratamiento", description = "Elimina un tratamiento por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Tratamiento eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Tratamiento no encontrado")
    })
    public void eliminar(@Parameter(description = "ID del tratamiento a eliminar") @PathVariable int id) {
        tratamientoService.delete(id);
    }

    @GetMapping("/por-chip/{chipId}")
    @Operation(summary = "Buscar tratamientos por chip ID", description = "Devuelve los tratamientos asociados a un perro usando su chip")
    public List<Tratamiento> obtenerPorChipId(
        @Parameter(description = "ID del chip del perro") @PathVariable String chipId) {
        return tratamientoService.findByPerroChipId(chipId);
    }

    @GetMapping("/por-diagnostico/{diagnostico}")
    @Operation(summary = "Buscar tratamientos por diagnóstico", description = "Filtra los tratamientos según el diagnóstico médico")
    public List<Tratamiento> obtenerPorDiagnostico(
        @Parameter(description = "Diagnóstico médico") @PathVariable String diagnostico) {
        return tratamientoService.findByDiagnostico(diagnostico);
    }

    @PatchMapping("/{id}/diagnostico")
    @Operation(summary = "Actualizar diagnóstico", description = "Permite modificar solo el diagnóstico del tratamiento")
    public Tratamiento actualizarDiagnostico(
        @Parameter(description = "ID del tratamiento") @PathVariable int id,
        @RequestBody(description = "Nuevo diagnóstico", required = true,
            content = @Content(schema = @Schema(type = "string"), examples = @ExampleObject(value = "\"Infección urinaria\"")))
        @org.springframework.web.bind.annotation.RequestBody String nuevoDiagnostico) {
        return tratamientoService.actualizarDiagnostico(id, nuevoDiagnostico);
    }

    @GetMapping("/{id}/duracion")
    @Operation(summary = "Obtener duración del tratamiento", description = "Devuelve la duración en días de un tratamiento")
    public int obtenerDuracion(@Parameter(description = "ID del tratamiento") @PathVariable int id) {
        return tratamientoService.obtenerDuracionTratamiento(id);
    }
}
